package net.contexx.thoth.core.model.valueprovider;

import net.contexx.thoth.core.model.common.DataType;

public abstract class Provider<T, Identity> implements ProviderTag<Identity> {
    public Provider(String domainUniqueName, DataType<T> datatype) {
        this.name = domainUniqueName;
        this.datatype = datatype;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // abstract

    public abstract T getValue(Identity identity, ExecutionContext executionContext);

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // name

    private final String name;

    public String getName() {
        return name;
    }

    //_____________________________________________________
    // datatype
    private DataType<T> datatype;

    public DataType<T> getDatatype() {
        return datatype;
    }
}
