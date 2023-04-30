package net.contexx.thoth.core.model.valueprovider;

import net.contexx.thoth.core.model.phasea.Variable;

public interface ExecutionContext {
    <T> T getValue(Variable<T> variable);
}
