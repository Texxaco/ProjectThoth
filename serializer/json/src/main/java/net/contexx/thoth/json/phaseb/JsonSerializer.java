package net.contexx.thoth.json.phaseb;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TSFBuilder;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.contexx.thoth.core.model.phaseb.Domain;
import net.contexx.thoth.json.phaseb.plugins.PluginRegister;

import java.io.IOException;
import java.util.Arrays;

public class JsonSerializer {
    private final ObjectMapper mapper;

    private final DomainJsonConverter domainJsonConverter;

    public JsonSerializer() { //todo name des parameters verbessern
        final DestinationJsonConverter destinationConverter = new DestinationJsonConverter();
        final AttributeValueJsonConverter attributeValueConverter = new AttributeValueJsonConverter();
        final TemplateJsonConverter templateJsonConverter = new TemplateJsonConverter(destinationConverter, attributeValueConverter);
        domainJsonConverter = new DomainJsonConverter(templateJsonConverter);

        int version = 1;
        domainJsonConverter.setOverallVersion(version);


        final TSFBuilder<?, ?> builder = JsonFactory.builder();
        this.mapper = new ObjectMapper(builder.build()); //todo IoC

        SimpleModule module = new SimpleModule("json serializer base", new Version(version, 0, 0, null, JsonSerializer.class.getPackageName(), "JsonSerializer")); //todo
        module.addSerializer(domainJsonConverter);
        module.addSerializer(templateJsonConverter);
        module.addSerializer(destinationConverter);
        module.addSerializer(attributeValueConverter);

        mapper.registerModule(module);

        mapper.registerModules(PluginRegister.getJacksonModules());
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //

    public String serialize(Domain<?> domain) {
        try {
            return mapper.writeValueAsString(domain);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <IdentityType> Domain<IdentityType> deserialize(net.contexx.thoth.core.model.phasea.Domain<IdentityType> domain, String data) throws IOException {
        try {
            final JsonNode node = mapper.readTree(data);
            return (Domain<IdentityType>) domainJsonConverter.load(node, domain);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex); //todo Exception verbessern
        }
    }
}
