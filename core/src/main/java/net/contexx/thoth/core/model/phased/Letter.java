package net.contexx.thoth.core.model.phased;

import net.contexx.thoth.core.model.common.attribute.AttributeValue;
import net.contexx.thoth.core.model.common.Domain;
import net.contexx.thoth.core.model.common.Sticky;
import net.contexx.thoth.core.model.phaseb.Destination;
import net.contexx.thoth.core.model.phasec.Document;

import java.util.Set;

public class Letter extends Sticky {
//    private Domain<?> domain;
//    private Document document;
//    private Destination destination;
//    private Set<AttributeValue<?>> attributeValues;

    private final byte[] content;

    public Letter(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }
}
