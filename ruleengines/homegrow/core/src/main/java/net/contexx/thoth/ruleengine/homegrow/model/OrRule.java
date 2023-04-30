package net.contexx.thoth.ruleengine.homegrow.model;

import net.contexx.thoth.core.model.common.attribute.Attributable;
import net.contexx.thoth.core.model.phasec.ExecutionContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrRule<IdentityType> implements Rule<IdentityType> {
    public OrRule(List<Rule<IdentityType>> rules) {
        this.rules = rules;
    }

    public OrRule(Rule<IdentityType>...rules) {
        this.rules = new ArrayList<>(Arrays.asList(rules));
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // rules
    private List<Rule<IdentityType>> rules;

    public List<Rule<IdentityType>> getRules() {
        return rules;
    }

    //todo access?

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // evaluation functionality


    @Override
    public EvaluationResult<IdentityType> evaluate(Attributable attributable, ExecutionContext<IdentityType> context) {
        final List<EvaluationResult<IdentityType>> nestedResults = rules.parallelStream().map(rule -> rule.evaluate(attributable, context)).collect(Collectors.toList());

        return new EvaluationResult<>(this, nestedResults.parallelStream().anyMatch(EvaluationResult::isAllowed), nestedResults){
            @Override
            public void prittyPrint(StringBuilder output, String prefix) {
                output.append(prefix).append("OR {\n");

                getNestedResults().forEach(elem -> {
                    elem.prittyPrint(output, prefix+"\t");
                    output.append("\n");
                });

                output.append(prefix).append("}");
            }
        };
    }
}
