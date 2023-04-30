package net.contexx.thoth.json.phaseb;

import net.contexx.thoth.core.model.common.DataType;
import net.contexx.thoth.core.model.phasea.DestinationType;
import net.contexx.thoth.core.model.phasea.variables.EntityIdentifier;
import net.contexx.thoth.core.model.phaseb.Destination;
import net.contexx.thoth.core.model.phaseb.Domain;
import net.contexx.thoth.core.model.phaseb.Template;
import net.contexx.qft.junit.QFT;
import net.contexx.qft.junit.assertions.QFTAsserts;
import net.contexx.thoth.json.phaseb.DummyRendererPlugin.DummyRenderInfo;
import net.contexx.thoth.json.phaseb.plugins.PluginRegister;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.UUID;

@ExtendWith(QFT.class)
class JsonSerializerTest {

    @Test
    void storeAndLoad() throws IOException {
        //given
        PluginRegister.register(new DummyRendererPlugin());

        final JsonSerializer jsonSerializer = new JsonSerializer();
        final EntityIdentifier<UUID> TEST_ENTITY = new EntityIdentifier<>("TestEntity", DataType.UUID);
        final DestinationType TEST_DESTINATION = new DestinationType("TestDestination");
        final net.contexx.thoth.core.model.phasea.Domain<UUID> phaseA_domain = new net.contexx.thoth.core.model.phasea.Domain<>("Foobar", TEST_ENTITY)
        .destinations(TEST_DESTINATION);

        final Domain<UUID> domain = new Domain<>(phaseA_domain);
        domain.add(new Template(UUID.fromString("00000000-0000-0000-0000-000000000002"), "Template1", new Destination(TEST_DESTINATION), new DummyRenderInfo()));
        domain.add(new Template(UUID.fromString("00000000-0000-0000-0000-000000000003"), "Template2", new Destination(TEST_DESTINATION), new DummyRenderInfo()));
        domain.add(new Template(UUID.fromString("00000000-0000-0000-0000-000000000004"), "Template3", new Destination(TEST_DESTINATION), new DummyRenderInfo()));

        //when
        final String json = jsonSerializer.serialize(domain);
        System.out.println(json);

        //then
        QFTAsserts.assertEquals("Storage Content", json);


        //when
        final Domain<UUID> deserializedDomain = jsonSerializer.deserialize(phaseA_domain, json);

        //then
        //todo check result
    }
}