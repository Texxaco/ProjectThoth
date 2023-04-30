package net.contexx.thoth.core.model.common.attribute;

import net.contexx.thoth.core.model.common.Sticky;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class Attributable extends Sticky {

    public Attributable(Set<AttributeValue<?>> attributeValues) {
        if(attributeValues != null) this.attributeValues.addAll(attributeValues);
    }

    private final Set<AttributeValue<?>> attributeValues = new HashSet<>();

    public Set<AttributeValue<?>> getAttributeValues() {
        return attributeValues; //todo unmodifiable list
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(Attribute<T> attribute) {
        return (T) attributeValues.parallelStream().filter(val -> val.getAttribute() == attribute).findAny().map(AttributeValue::getValue).orElse(null);
    }

    public synchronized <T>  void setValue(Attribute<T> attribute, T value) {
        final Optional<AttributeValue<?>> attribVal = attributeValues.parallelStream().filter(av -> av.getAttribute() == attribute).findFirst();
        if(attribVal.isPresent()) {
            //noinspection unchecked
            ((AttributeValue<T>)attribVal.get()).setValue(value);
        } else {
            attributeValues.add(new AttributeValue<>(attribute, value));
        }
    }

    public boolean hasAttribute(Attribute<?> attribute) {
        return attributeValues.contains(attribute);
    }
}
