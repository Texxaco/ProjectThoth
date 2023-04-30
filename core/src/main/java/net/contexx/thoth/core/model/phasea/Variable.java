package net.contexx.thoth.core.model.phasea;

import net.contexx.thoth.core.model.common.DataType;

public abstract class Variable<T> extends AbstractEntity {

    public Variable(String name, DataType<? super T> datatype) {
        this.name = name;
        this.datatype = datatype;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // name

    private final String name;

    public String getName() {
        return name;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // datatype

    private final DataType<? super T> datatype;

    public DataType<? super T> getDataType() {
        return datatype;
    }

//    //_________________________________________________________________________
//    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//    // variables
//
//    private T value;
//
//    public T getValue() {
//        return value;
//    }
//
//    public void setValue(T value) {
//        this.value = value;
//    }
}
