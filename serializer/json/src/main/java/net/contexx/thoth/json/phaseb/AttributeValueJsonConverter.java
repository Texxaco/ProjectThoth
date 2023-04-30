package net.contexx.thoth.json.phaseb;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.contexx.thoth.core.model.common.attribute.Attribute;
import net.contexx.thoth.core.model.common.attribute.AttributeRegister;
import net.contexx.thoth.core.model.common.attribute.AttributeValue;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.json.core.OptionalAbstractJsonConverter;

import java.io.IOException;
import java.util.Optional;

public class AttributeValueJsonConverter extends OptionalAbstractJsonConverter<AttributeValue<?>> {

    private static final String MODULE = "module";
    private static final String NAME = "name";
    private static final String DATATYPE = "datatype";
    private static final String VALUE = "value";

    public AttributeValueJsonConverter() {
        super(AttributeValue.class, true);
    }

    @Override
    public void serialize(AttributeValue<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(MODULE, value.getAttribute().getName());
        gen.writeStringField(NAME, value.getAttribute().getName());
        gen.writeStringField(DATATYPE, value.getAttribute().getDatatype().getName());
        gen.writeStringField(VALUE, convertValueToString(value)); //todo
        gen.writeEndObject();
    }

    private <T> String convertValueToString(AttributeValue<T> value) {
        return value.getAttribute().getDatatype().toString(value.getValue());
    }

    public Optional<AttributeValue<?>> load(JsonNode node, Domain<?> domain, int version) {
        final String module = node.get(MODULE).asText();
        final String name = node.get(NAME).asText();

        final Optional<Attribute<?>> optionalAttribute = AttributeRegister.find(module, name);


        //noinspection unchecked,rawtypes
        return optionalAttribute.map(
                attribute -> new AttributeValue(
                        attribute, attribute.getDatatype().valueOf(
                            node.get(VALUE).asText()
                        )
                )
        );
    }
}
