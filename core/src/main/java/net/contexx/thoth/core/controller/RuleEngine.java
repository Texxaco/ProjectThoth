package net.contexx.thoth.core.controller;

import net.contexx.thoth.core.model.common.attribute.Attributable;
import net.contexx.thoth.core.model.phaseb.RuleSet;
import net.contexx.thoth.core.model.phasec.ExecutionContext;

public interface RuleEngine {


    /**
     *
     * @param attributable never null
     * @param ruleSet      never null
     * @param context      never null
     * @return
     * @param <IdentityType>
     * @throws RuleEngineExecutionException
     */
    <IdentityType> EvaluationResult evaluate(Attributable attributable, RuleSet ruleSet, ExecutionContext<IdentityType> context) throws RuleEngineExecutionException;

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // evaluation result

    String prittyPrint(EvaluationResult evalResult);

    interface EvaluationResult {
        boolean isAllowed();
    }

    class GenericEvaluationResult implements EvaluationResult {

        private final boolean allowed;
        private final String description;

        public GenericEvaluationResult(boolean allowed, String description) {
            this.allowed = allowed;
            this.description = description;
        }

        @Override
        public boolean isAllowed() {
            return allowed;
        }

        @Override
        public String toString() {
            return "[Generic Result] > "+isAllowed()+"\n\t- "+description.replace("\n", "\n\t- ");
        }
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // exceptions

    class RuleEngineExecutionException extends Exception {
        public RuleEngineExecutionException(String message) {
            super(message);
        }

        public RuleEngineExecutionException(String message, Throwable cause) {
            super(message, cause);
        }

        public RuleEngineExecutionException(Throwable cause) {
            super(cause);
        }

        public RuleEngineExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
