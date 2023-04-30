package net.contexx.thoth.json.phaseb;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.contexx.thoth.core.common.AbandonPlaceholder;
import net.contexx.thoth.core.model.phasea.DestinationType;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.core.model.phaseb.Destination;
import net.contexx.thoth.json.core.AbstractJsonConverter;

import java.io.IOException;
import java.util.Set;

import static net.contexx.thoth.json.core.JsonConverterUtils.loadArray;
import static net.contexx.thoth.json.core.JsonConverterUtils.writeArray;

public class DestinationJsonConverter extends AbstractJsonConverter<Destination> {

    private static final String NAME = "name";
    private static final String ATTRIBUTES = "attributes";
    private static final String RULESET = "ruleset";

    public DestinationJsonConverter() {
        super(Destination.class);
    }

    @Override
    public void serialize(Destination value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(NAME, value.getType().getName());

        writeArray(ATTRIBUTES, value.getAttributeValues(), attributeValue -> attributeValue.getAttribute().getName(), gen);

        if (value.getRuleSet() != null) {
            RuleSetAdapter.serialize(gen, value.getRuleSet());
        }

        gen.writeEndObject();
    }

    public Destination load(JsonNode node, Domain<?> domain, int version) throws IOException { //todo versioniertes laden implementieren
        final String name = node.get(NAME).asText();

        final Set<DestinationType> destinations = domain.getDestinations();

        final Destination destination = new Destination(
                destinations.parallelStream().filter(t -> t.getName().equals(name)).findFirst().orElse(AbandonPlaceholder.getAbandonedDestination(name)), //todo sollte in Phase B schon abandon genutzt werden?
                loadArray(node.get(ATTRIBUTES), new AttributeValueJsonConverter(), domain, version)
        );

        RuleSetAdapter.load(node, domain).ifPresent(destination::setRuleSet);

        return destination;
    }
}
