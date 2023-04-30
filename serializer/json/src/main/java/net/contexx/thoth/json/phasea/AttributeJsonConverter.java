package net.contexx.thoth.json.phasea;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.contexx.thoth.core.model.common.DataType;
import net.contexx.thoth.core.model.common.attribute.Attribute;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.json.core.AbstractJsonConverter;

import java.io.IOException;

public class AttributeJsonConverter extends AbstractJsonConverter<Attribute<?>> {

    private static final String MODULE = "module";
    private static final String NAME = "name";
    private static final String DATATYPE = "datatype";

    public AttributeJsonConverter() {
        super(Attribute.class, true);
    }

    @Override
    public void serialize(Attribute<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(MODULE, value.getName());
        gen.writeStringField(NAME, value.getName());
        gen.writeStringField(DATATYPE, value.getDatatype().getName());
        gen.writeEndObject();
    }

    public Attribute<?> load(JsonNode node, Domain<?> domain, int version) {
        final String module = node.get(MODULE).asText();
        final String name = node.get(NAME).asText();

        return new Attribute<>(module, name, DataType.find(node.get(DATATYPE).asText()).orElse(DataType.UNKNOWN)
//                destinations.parallelStream().filter(t -> t.getName().equals(name)).findFirst().orElse(AbandonPlaceholder.getAbandonedDestination(name)) //todo
        );
    }
}
