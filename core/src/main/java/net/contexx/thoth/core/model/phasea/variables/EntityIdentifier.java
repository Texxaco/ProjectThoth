package net.contexx.thoth.core.model.phasea.variables;

import net.contexx.thoth.core.model.common.DataType;
import net.contexx.thoth.core.persistence.Storable;

public class EntityIdentifier<T> extends PersistantVariable<T> implements Storable<T> {
    public EntityIdentifier(String name, DataType<T> datatype) {
        super(name, datatype);
    }

    @Override
    public String serialize(T value) {
        return getDataType().toString(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(String data) {
        return (T) getDataType().valueOf(data);
    }
}
