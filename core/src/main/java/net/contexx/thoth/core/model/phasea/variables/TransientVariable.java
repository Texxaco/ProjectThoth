package net.contexx.thoth.core.model.phasea.variables;

import net.contexx.thoth.core.model.common.DataType;
import net.contexx.thoth.core.model.phasea.Variable;

public class TransientVariable<T> extends Variable<T> {
    @SuppressWarnings("unchecked")
    public TransientVariable(String name, Class<T> datatype) {
        super(name, (DataType<? super T>) DataType.find(datatype).orElse(DataType.UNKNOWN));
    }
}
