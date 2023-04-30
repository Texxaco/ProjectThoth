package net.contexx.thoth.core.model.phasec;

import net.contexx.thoth.core.model.common.attribute.Attributable;
import net.contexx.thoth.core.model.common.attribute.AttributeValue;

import java.util.Set;

public abstract class Addressee extends Attributable {
    public Addressee(Set<AttributeValue<?>> attributeValues) {
        super(attributeValues);
    }

    public Addressee() {
        super(null);
    }
}
