package net.contexx.thoth.ruleengine.homegrow.model;

import net.contexx.thoth.core.controller.RuleEngine;
import net.contexx.thoth.core.model.common.attribute.Attributable;
import net.contexx.thoth.core.model.phaseb.RuleSet;
import net.contexx.thoth.core.model.phasec.ExecutionContext;

import java.util.Collections;
import java.util.List;

public interface Rule<IdentityType> extends RuleSet {
    EvaluationResult<IdentityType> evaluate(Attributable attributable, ExecutionContext<IdentityType> context);

    abstract class  EvaluationResult<IdentityType> implements RuleEngine.EvaluationResult {
        private final Rule<IdentityType> parent;
        private final boolean result;
        private final List<EvaluationResult<IdentityType>> nestedResults;

        public EvaluationResult(Rule<IdentityType> parent, boolean result, List<EvaluationResult<IdentityType>> nestedResults) {
            this.parent = parent;
            this.result = result;
            this.nestedResults = nestedResults;
        }

        public EvaluationResult(Rule<IdentityType> parent, boolean result) {
            this(parent, result, Collections.emptyList());
        }

        public Rule<?> getParent() {
            return parent;
        }

        public boolean isAllowed() {
            return result;
        }

        public List<EvaluationResult<IdentityType>> getNestedResults() {
            return nestedResults;
        }

        public abstract void prittyPrint(StringBuilder output, String prefix);
    }
}
