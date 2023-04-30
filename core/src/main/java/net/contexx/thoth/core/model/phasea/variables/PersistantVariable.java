package net.contexx.thoth.core.model.phasea.variables;

import net.contexx.thoth.core.model.common.DataType;
import net.contexx.thoth.core.model.phasea.Variable;
import net.contexx.thoth.core.persistence.ByteStorable;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PersistantVariable<T> extends Variable<T> implements ByteStorable<T> { //todo Prüfen ob diese Klasse abstrakt sein darf.
    public PersistantVariable(String name, DataType<T> datatype) {
        super(name, checkDataType(datatype));
    }

    private static <T> DataType<T> checkDataType(DataType<T> dataType) {
        if(dataType == DataType.UNKNOWN) throw new RuntimeException("DataType "+DataType.UNKNOWN.getName()+" is not allowed for persistant variables");
        return dataType;
    }

    @Override
    public byte[] serializeToBytes(T value) {
        return getDataType().toString(value).getBytes(UTF_8);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserializeFromBytes(byte[] data) {
        return (T) getDataType().valueOf(new String(data, UTF_8));
    }
}
