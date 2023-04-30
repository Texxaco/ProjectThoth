package net.contexx.thoth.core.model.common.attribute;

import java.util.Objects;

public class AttributeValue<T> {

    public AttributeValue(Attribute<T> attribute, T value) {
        this.attribute = attribute;
        this.value = value;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // properties

    private Attribute<T> attribute;

    public Attribute<T> getAttribute() {
        return attribute;
    }

    //_____________________________________________________
    //
    private T value;

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // equal & hashcode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeValue<?> that)) return false;
        return attribute.equals(that.attribute) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, value);
    }
}
