package net.contexx.thoth.json.phaseb;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.contexx.thoth.core.controller.RenderEngine;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.core.model.phaseb.Destination;
import net.contexx.thoth.core.model.phaseb.RuleSet;
import net.contexx.thoth.core.model.phaseb.Template;
import net.contexx.thoth.core.model.common.attribute.AttributeValue;
import net.contexx.thoth.json.core.AbstractJsonConverter;
import net.contexx.thoth.json.core.OptionalAbstractJsonConverter;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static net.contexx.thoth.json.core.JsonConverterUtils.loadArray;
import static net.contexx.thoth.json.core.JsonConverterUtils.writeArray;

public class TemplateJsonConverter extends AbstractJsonConverter<Template> {

    public static final String NAME = "name";
    public static final String IDENTIFIER = "identifier";
    public static final String DESTINATION = "destinations";
    public static final String ATTRIBUTES = "attributes";

    private final AbstractJsonConverter<Destination> destinationConverter;
    private final OptionalAbstractJsonConverter<AttributeValue<?>> attributeValueConverter;

    public TemplateJsonConverter(AbstractJsonConverter<Destination> destinationConverter, OptionalAbstractJsonConverter<AttributeValue<?>> attributeValueConverter) {
        super(Template.class);

        this.destinationConverter = destinationConverter;
        this.attributeValueConverter = attributeValueConverter;
    }

    @Override
    public void serialize(Template value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(NAME, value.getName());
        gen.writeObjectField(IDENTIFIER, value.getIdentifier());

        writeArray(ATTRIBUTES, value.getAttributeValues(), attributeValue -> attributeValue.getAttribute().getName(), gen);
        writeArray(DESTINATION, value.getDestinations(), destination -> destination.getType().getName(), gen);

        RuleSetAdapter.serialize(gen, value.getRuleSet());
        RenderInfoAdapter.serialize(gen, value.getRendererInfo());

        gen.writeEndObject();
    }

    public Template load(JsonNode node, Domain<?> domain, int version) throws IOException {
        final String name = node.get(NAME).asText();
        final UUID identifier = UUID.fromString(node.get(IDENTIFIER).asText());

        final Set<AttributeValue<?>> attributes = loadArray(node.get(ATTRIBUTES), attributeValueConverter, domain, version);

        final Optional<RuleSet> ruleSet = RuleSetAdapter.load(node, domain);
        final RenderEngine.RenderInfo renderInfo = RenderInfoAdapter.load(node, domain, name);

        final Template result = new Template(
                identifier,
                name,
                loadArray(node.get(DESTINATION), destinationConverter, domain, version),
                renderInfo, //todo
                attributes
        );

        ruleSet.ifPresent(result::setRuleSet);

        return result;
    }


}
