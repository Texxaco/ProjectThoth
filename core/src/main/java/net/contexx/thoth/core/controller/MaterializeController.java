package net.contexx.thoth.core.controller;

import net.contexx.thoth.core.controller.RuleEngine.GenericEvaluationResult;
import net.contexx.thoth.core.model.common.Key;
import net.contexx.thoth.core.model.common.attribute.Attributable;
import net.contexx.thoth.core.model.common.attribute.Attribute;
import net.contexx.thoth.core.model.common.attribute.AttributeValue;
import net.contexx.thoth.core.model.phasea.Symbol;
import net.contexx.thoth.core.model.phaseb.Destination;
import net.contexx.thoth.core.model.phaseb.Domain;
import net.contexx.thoth.core.model.phaseb.RuleSet;
import net.contexx.thoth.core.model.phaseb.Template;
import net.contexx.thoth.core.model.phasec.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class MaterializeController {

//    private static final Key<RuleEngine.EvaluationResult> KEY_TEMPLATE_EVALUATION_RESULT = new Key<>("TemplateEvaluationResult");
//    private static final Key<RuleEngine.EvaluationResult> KEY_DESTINATION_EVALUATION_RESULT = new Key<>("DestinationEvaluationResult");
    private static final Key<List<ReasoningContainer>> KEY_REASONINGS = new Key<>("Reasoning");

    private final DomainRegister domainRegister;
    private final AddresseeProvider addresseeProvider;
    private final RuleEngine ruleEngine;

    public MaterializeController(DomainRegister domainRegister, AddresseeProvider addresseeProvider, RuleEngine ruleEngine) {
        this.domainRegister = domainRegister;
        this.addresseeProvider = addresseeProvider;
        this.ruleEngine = ruleEngine;
    }

    public <E> Folder materialize(final net.contexx.thoth.core.model.phasea.Domain<E> domain, final Symbol symbol, E entityIdent, final Variables variables) {
        final Domain<E> domainPhaseB = domainRegister.getDomain(domain);

        //todo handle entityIdent

        final Folder folder = new Folder(symbol);
        domainPhaseB.getTemplates().parallelStream().forEach(template -> {
            final RuleEngine.EvaluationResult templateEvalResult = evaluate(domain, template, template.getRuleSet(), symbol, variables);
            if(templateEvalResult.isAllowed()) {
                template.getDestinations().parallelStream().forEach(destination -> {

                    final RuleEngine.EvaluationResult destinationEvalResult = evaluate(domain, destination, destination.getRuleSet(), symbol, variables);
                    if(destinationEvalResult.isAllowed()) {

                        toAddressees(entityIdent, destination).forEach(addressee -> {
                            folder.add(
                                    new Document(
                                            template.getAttributeValues().parallelStream().map(this::copyAttributeValue).collect(Collectors.toSet()),
                                            template.getName(),
                                            addressee,
                                            template.getRendererInfo())
                            );
                        });

                    }

                    folder.stickIfAbsent(KEY_REASONINGS, k -> new CopyOnWriteArrayList<>()).add(new ReasoningContainer(template, templateEvalResult, destination, destinationEvalResult, ruleEngine));
                });
            } else {
                folder.stickIfAbsent(KEY_REASONINGS, k -> new CopyOnWriteArrayList<>()).add(new ReasoningContainer(template, templateEvalResult, ruleEngine));
            }
        });

        return folder;
    }

    private <E> RuleEngine.EvaluationResult evaluate(net.contexx.thoth.core.model.phasea.Domain<E> domainPhaseA, Attributable attributable, RuleSet ruleSet, Symbol symbol, Variables variables) {
        if(ruleEngine == null) return new GenericEvaluationResult(true, "No RuleEngine given.");
        if(ruleSet == null) return new GenericEvaluationResult(true, "No RuleSet given.");

        final ExecutionContext<E> context = new ExecutionContext<>(
                domainPhaseA,
                variables
        );

        try {
             return ruleEngine.evaluate(attributable, ruleSet, context);
        } catch (RuleEngine.RuleEngineExecutionException e) {
            throw new RuntimeException(e); //todo put in a better exception
        }
    }

    private <E> Set<Addressee> toAddressees(E entityIdent, Destination destination) { //todo handle entityIdent
        final Set<Addressee> addressees = addresseeProvider.resolve(destination);

        addressees.forEach(addressee -> {
            destination.getAttributeValues().parallelStream().forEach(av -> copyAddAttributeValue(av, addressee));
        });

        return addressees;
    }

    private <T> AttributeValue<T> copyAttributeValue(AttributeValue<T> av) {
        final Attribute<T> attribute = av.getAttribute();
        return new AttributeValue<T>(av.getAttribute(), attribute.getDatatype().copy(av.getValue()));
    }

    private <T> void copyAddAttributeValue(AttributeValue<T> av, Attributable attributable) {
        final Attribute<T> attribute = av.getAttribute();
        attributable.setValue(av.getAttribute(), attribute.getDatatype().copy(av.getValue()));
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // reasoning

    public String reasoningMaterialization(Folder folder) {
        final List<ReasoningContainer> reasoning = folder.getOrDefault(KEY_REASONINGS, Collections.emptyList());

        final StringBuilder output = new StringBuilder("===================================\n");

        reasoning.stream().sorted((o1, o2) -> {
            int result = o1.getTemplate().getName().compareTo(o2.getTemplate().getName());

            if(result == 0) {
                if (o1.getDestination() == null && o2.getDestination() != null) return 1;
                if (o1.getDestination() != null && o2.getDestination() == null) return -1;

                result = o1.getDestination().getType().getName().compareTo(o2.getDestination().getType().getName());
            }

            return result;
        }).forEachOrdered(r -> {
            if(r.getDestination() == null) { //todo
                prittyPrintTemplate(r.getTemplate(), r.getEvaluationResult(), output);
            } else {
                prittyPrintTemplate(r.getTemplate(), r.getEvaluationResult(), output);
                prittyPrintDestination(r.getDestination(), r.getDestinationEvalResult(), output);
            }
            output.append("===================================\n");
        });

        return output.toString();
    }

    private void prittyPrintTemplate(Template template, RuleEngine.EvaluationResult evalResult, StringBuilder output) {
        output.append("[Template name=\"").append(template.getName())
                   .append("\" domain=\"").append(template.getDomain().getName())
                       .append("\" id=\"").append(template.getIdentifier())
                   .append("\" result=\"").append(evalResult.isAllowed())
                .append("\"]\n> Evaluation Result\n");

        output.append("\t").append(ruleEngine.prittyPrint(evalResult).replace("\n", "\n\t")).append("\n");
    }

    private void prittyPrintDestination(Destination destination, RuleEngine.EvaluationResult destinationEvalResult, StringBuilder output) {
        output.append("\t[Destination name=\"").append(destination.getType().getName())
                        .append("\" result=\"").append(destinationEvalResult.isAllowed())
              .append("\"]\n\t> Evaluation Result\n");
        output.append("\t\t").append(ruleEngine.prittyPrint(destinationEvalResult).replace("\n", "\n\t\t")).append("\n");
    }

    private static class ReasoningContainer {
        private final Template template;
        private final RuleEngine.EvaluationResult evaluationResult;
        private final Destination destination;
        private final RuleEngine.EvaluationResult destinationEvalResult;
        private final RuleEngine usedRuleEngine;

        public ReasoningContainer(Template template, RuleEngine.EvaluationResult templateEvalResult, Destination destination, RuleEngine.EvaluationResult destinationEvalResult,  RuleEngine ruleEngine) {
            this.template = template;
            this.evaluationResult = templateEvalResult;
            this.destination = destination;
            this.destinationEvalResult = destinationEvalResult;
            this.usedRuleEngine = ruleEngine;
        }

        public ReasoningContainer(Template template, RuleEngine.EvaluationResult evaluationResult, RuleEngine ruleEngine) {
            this.template = template;
            this.evaluationResult = evaluationResult;
            this.destination = null;
            this.destinationEvalResult = null;
            this.usedRuleEngine = ruleEngine;
        }

        public Template getTemplate() {
            return template;
        }

        public RuleEngine.EvaluationResult getEvaluationResult() {
            return evaluationResult;
        }

        public Destination getDestination() {
            return destination;
        }

        public RuleEngine.EvaluationResult getDestinationEvalResult() {
            return destinationEvalResult;
        }

        public RuleEngine getUsedRuleEngine() {
            return usedRuleEngine;
        }
    }
}
