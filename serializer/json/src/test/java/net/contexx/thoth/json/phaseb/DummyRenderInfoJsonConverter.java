package net.contexx.thoth.json.phaseb;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.json.core.OptionalAbstractJsonConverter;

import java.io.IOException;
import java.util.Optional;

public class DummyRenderInfoJsonConverter extends OptionalAbstractJsonConverter<DummyRendererPlugin.DummyRenderInfo> {
    public DummyRenderInfoJsonConverter() {
        super(DummyRendererPlugin.DummyRenderInfo.class, true);
    }

    @Override
    public void serialize(DummyRendererPlugin.DummyRenderInfo value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeEndObject();
    }

    public Optional<DummyRendererPlugin.DummyRenderInfo> load(JsonNode node, Domain<?> domain, int version) throws IOException {
        return Optional.of(new DummyRendererPlugin.DummyRenderInfo());
    }
}
