package net.contexx.thoth.core.model.common.attribute;

import net.contexx.thoth.core.model.common.DataType;

public class Attribute<T> {

    public Attribute(String module, String name, DataType<T> datatype) {
        this.module = module;
        this.name = name;
        this.datatype = datatype;

        AttributeRegister.add(this);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes


    private String module;

    /**
     * This property contains the (business od development) name of the name, this Attribute object
     * belongs to. This is to avoid nameing conflicts across different modules and helps to track down the origin of a
     * specific attribute.
     * @return a module name
     */
    public String getModule() {
        return module;
    }

    //_____________________________________________________
    //

    private String name;

    public String getName() {
        return name;
    }

    //_____________________________________________________
    //

    private DataType<T> datatype;

    public DataType<T> getDatatype() {
        return datatype;
    }
}
