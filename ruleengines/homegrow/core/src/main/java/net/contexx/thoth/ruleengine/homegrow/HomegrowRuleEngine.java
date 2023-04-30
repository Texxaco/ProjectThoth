package net.contexx.thoth.ruleengine.homegrow;

import net.contexx.thoth.core.model.common.attribute.Attributable;
import net.contexx.thoth.core.model.phaseb.RuleSet;
import net.contexx.thoth.core.model.phasec.ExecutionContext;
import net.contexx.thoth.ruleengine.homegrow.model.Rule;

public class HomegrowRuleEngine implements net.contexx.thoth.core.controller.RuleEngine {
    @SuppressWarnings("unchecked")
    @Override
    public <I> EvaluationResult evaluate(Attributable attributable, RuleSet ruleSet, ExecutionContext<I> context) throws RuleEngineExecutionException {
        assert attributable != null : "attributable must not be null";
        assert  context != null : "context must not be null";

        if(ruleSet instanceof Rule) {
            return ((Rule<I>) ruleSet).evaluate(attributable, context);
        } else throw new UnsupportedRuleSetException(ruleSet.getClass());
    }

    @Override
    public String prittyPrint(EvaluationResult evalResult) {
        if(evalResult instanceof Rule.EvaluationResult<?> homeGrowEvalResult) {
            final StringBuilder output = new StringBuilder();
            homeGrowEvalResult.prittyPrint(output, "");
            return output.toString();
        }
        return evalResult.toString();
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // exceptions

    private static class UnsupportedRuleSetException extends RuleEngineExecutionException {
        public UnsupportedRuleSetException(Class<?> ruleSetClass) {
            super("Unsupported RuleSet (Class: "+ruleSetClass.getName()+")");
        }
    }
}
