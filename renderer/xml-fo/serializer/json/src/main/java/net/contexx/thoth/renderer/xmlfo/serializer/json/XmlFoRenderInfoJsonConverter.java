package net.contexx.thoth.renderer.xmlfo.serializer.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.json.core.OptionalAbstractJsonConverter;
import net.contexx.thoth.renderer.xmlfo.XmlFoRenderInfo;

import java.io.IOException;
import java.util.Optional;

import static net.contexx.thoth.json.core.JsonConverterUtils.writeArray;

public class XmlFoRenderInfoJsonConverter extends OptionalAbstractJsonConverter<XmlFoRenderInfo> {

    private static final String TEMPLATE_IDENTIFIER = "templateIdentifier";

    public XmlFoRenderInfoJsonConverter() {
        super(XmlFoRenderInfo.class, true);
    }

    @Override
    public void serialize(XmlFoRenderInfo value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(TEMPLATE_IDENTIFIER, value.getTemplateIdentifier());
        gen.writeEndObject();
    }

    public Optional<XmlFoRenderInfo> load(JsonNode node, Domain<?> domain, int version) throws IOException {
        return Optional.of(new XmlFoRenderInfo(node.get(TEMPLATE_IDENTIFIER).asText()));
    }
}
