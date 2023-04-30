package net.contexx.thoth.json.phaseb;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.contexx.thoth.core.model.phaseb.Domain;
import net.contexx.thoth.core.model.phaseb.Template;
import net.contexx.thoth.json.core.AbstractJsonConverter;

import java.io.IOException;
import java.util.Comparator;

public class DomainJsonConverter extends AbstractJsonConverter<Domain<?>> {

    public static final String VERSION = "version";
    public static final String ORIGIN = "origin";
//    public static final String IDENTIFIER = "identifier";
    public static final String TEMPLATES = "templates";
    private final AbstractJsonConverter<Template> templateConverter;

    public DomainJsonConverter(AbstractJsonConverter<Template> templateConverter) {
        super(Domain.class, true);

        this.templateConverter = templateConverter;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // version

    private int overallVersion = -1;

    public void setOverallVersion(int version) {
        this.overallVersion = version;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // implementation

    @Override
    public void serialize(Domain<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(VERSION, overallVersion);
        gen.writeStringField(ORIGIN, value.getOriginDomain().getName());
//        gen.writeObjectField(IDENTIFIER, value.getIdentifier());

        gen.writeArrayFieldStart(TEMPLATES);
        for (Template template : value.getTemplates().stream().sorted(Comparator.comparing(Template::getIdentifier)).toList()) {
            gen.writeObject(template);
        }
        gen.writeEndArray();

        gen.writeEndObject();
    }

    @SuppressWarnings("unchecked")
    public <T> Domain<T> load(JsonNode node, net.contexx.thoth.core.model.phasea.Domain<T> domain) throws IOException {
        final int version = node.get(VERSION).asInt(-1);
        return (Domain<T>) this.load(node, domain, version);
    }

    public Domain<?> load(JsonNode node, net.contexx.thoth.core.model.phasea.Domain<?> domain, int version) throws IOException {
        final String origin = node.get(ORIGIN).asText();
        if(!domain.getName().equals(origin)) throw new RuntimeException("TODO"); //todo Exception verbessern.

//        final UUID identifier = UUID.fromString(node.get(IDENTIFIER).asText());

        final Domain<?> result = new Domain<>(domain);

        final JsonNode templates = node.get(TEMPLATES);
        for(int i=0, s=templates.size();i<s;i++){
            result.add(templateConverter.load(templates.get(i), domain, version)); //todo auf Versioniertes laden umstellen
        }

        return result;
    }
}
