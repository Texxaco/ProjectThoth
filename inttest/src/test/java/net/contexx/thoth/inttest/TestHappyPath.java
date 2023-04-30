package net.contexx.thoth.inttest;

import net.contexx.thoth.core.controller.*;
import net.contexx.thoth.core.model.common.DataType;
import net.contexx.thoth.core.model.common.attribute.modules.DefaultAttributes;
import net.contexx.thoth.core.model.phaseb.Destination;
import net.contexx.thoth.core.model.phaseb.Domain;
import net.contexx.thoth.core.model.phaseb.Template;
import net.contexx.thoth.core.model.phasec.Folder;
import net.contexx.thoth.core.model.phasec.Variables;
import net.contexx.thoth.core.model.phased.Envelopes;
import net.contexx.thoth.core.persistence.phacec.Storage;
import net.contexx.thoth.inttest.inmemory.InMemoryAddresseeProvider;
import net.contexx.thoth.inttest.inmemory.InMemoryPhaseCStorage;
import net.contexx.thoth.json.phaseb.JsonSerializer;
import net.contexx.thoth.json.phaseb.plugins.PluginRegister;
import net.contexx.thoth.renderer.xmlfo.XmlFoRenderer;
import net.contexx.thoth.ruleengine.homegrow.HomegrowRuleEngine;
import net.contexx.thoth.ruleengine.homegrow.model.Condition;
import net.contexx.thoth.ruleengine.homegrow.model.Operators;
import net.contexx.qft.junit.QFT;
import net.contexx.qft.junit.assertions.QFTAsserts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static java.util.Collections.singletonList;
import static net.contexx.thoth.inttest.IntTestDomain.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(QFT.class)
public class TestHappyPath {

    private static final int IDENTITY = 123;

    @Test
    void happyPath() throws Exception {
        //given
        PluginRegister.register(new net.contexx.thoth.ruleengine.homegrow.serializer.json.JsonSerializerPlugin());
        PluginRegister.register(new net.contexx.thoth.renderer.xmlfo.serializer.json.JsonSerializerPlugin());

        final JsonSerializer jsonSerializer = new JsonSerializer();

        final DomainRegister domainRegister = new DomainRegister();

        final InMemoryAddresseeProvider addresseeProvider = new InMemoryAddresseeProvider()
                .add(DEST_A, "Linus")
                .add(DEST_B, "Max")
                .add(DEST_B, "Vanessa");

        final Storage phase_c_storage = new InMemoryPhaseCStorage(); //todo durch einen echten Store ersetzen

        final HomegrowRuleEngine ruleEngine = new HomegrowRuleEngine();

        final XmlFoRenderer renderer = new XmlFoRenderer();

        final ArchivController archivController = new ArchivController(new Archiv() { }); //todo

        final SenderController senderController = new SenderController(new Sender() { }); //todo

        //--------------------------------------------------------------------------------------------------------------
        //when
        final String phaseBConfig = setupPhaseBConfiguration(jsonSerializer, renderer);
        QFTAsserts.assertEquals("phaseBConfig", phaseBConfig);
//        System.out.println(phaseBConfig);

        //load phase b configuration
        final Domain<Integer> domain_phase_b = jsonSerializer.deserialize(INT_TEST_DOMAIN, phaseBConfig);
        assertNotNull(domain_phase_b);
        assertSame(INT_TEST_DOMAIN, domain_phase_b.getOriginDomain());

        domainRegister.register(domain_phase_b);

        //render to phase c

        final MaterializeController materializeController = new MaterializeController(domainRegister, addresseeProvider, ruleEngine);

        Folder folder = materializeController.materialize(INT_TEST_DOMAIN, SYMBOL_A, IDENTITY, new Variables());
        assertNotNull(folder);

        final String reasoning = materializeController.reasoningMaterialization(folder);
        System.out.println(reasoning);
        QFTAsserts.assertEquals("reasoning", reasoning);

        System.out.println("\nDocuments:");
        folder.getDocuments().forEach(document -> System.out.println(document.getName() + " -> " + document.getAddressee()));

        assertEquals(2, folder.getDocuments().size());

            //persist phase c
            phase_c_storage.store(folder);

            //load phase c from persistence
            folder = phase_c_storage.load(folder.getUUID());

            //mark one document "to be printed"
            folder.getDocuments().forEach(document -> {
                document.setValue(DefaultAttributes.ACTIVE, true);
            });

            //persist phase c
            phase_c_storage.store(folder);

        //render to phase d
        RenderController renderController = new RenderController(renderer);
        Envelopes envelopes = renderController.render(INT_TEST_DOMAIN, IDENTITY, folder, new Variables()); //todo
        assertNotNull(envelopes);
        assertEquals(2, envelopes.getEnvelopSet().size());

//        QFTAsserts.assertEquals("Letter A Content",  BYTE_ARRAY, envelopes.getEnvelopSet().parallelStream().filter(envelop -> envelop.getDocument().getName().equals("Template A")).findFirst().orElseGet(fail("Template A not found.")).getLetter().getContent()); //todo
//        QFTAsserts.assertEquals("Letter C Content",  BYTE_ARRAY, envelopes.getEnvelopSet().parallelStream().filter(envelop -> envelop.getDocument().getName().equals("Template A")).findFirst().orElseGet(fail("Template A not found.")).getLetter().getContent()); //todo

        //archiv and send
        archivController.archiv(INT_TEST_DOMAIN, IDENTITY, envelopes);
        senderController.send(envelopes);
        archivController.markAsSend(INT_TEST_DOMAIN, IDENTITY, envelopes);

        //then
//        assertEquals(2, archivController.getEnvelopes(INT_TEST_DOMAIN, IDENTITY).size()); //todo
    }

    private String setupPhaseBConfiguration(JsonSerializer jsonSerializer, XmlFoRenderer renderer) {
        final Domain domain = new Domain(INT_TEST_DOMAIN);
        domain.add(new Template(
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "Template A", new Destination(DEST_A),
                renderer.getRenderInfo("sample.template.xml")
        )).setRuleSet(
                new Condition(
                        DataType.STRING,
                        INT_TEST_DOMAIN.findProvider("Another Domain->Sample Subdomain Provider").orElseThrow(),
                        Operators.EQUALS,
                        singletonList(String.valueOf(IDENTITY))
                )
        );

        domain.add(new Template(
                UUID.fromString("00000000-0000-0000-0000-000000000002"),
                "Template B", new Destination(DEST_B),
                renderer.getRenderInfo("sample.template.xml")
        ));

        domain.add(new Template(
                UUID.fromString("00000000-0000-0000-0000-000000000003"),
                "This Template is not allowed by rules", new Destination(DEST_B),
                renderer.getRenderInfo("sample.template.xml")
        )).setRuleSet(
                new Condition(
                        DataType.BOOLEAN,
                        INT_TEST_DOMAIN.findProvider("Sample Boolean Provider").orElseThrow(),
                        Operators.EQUALS,
                        singletonList(false)
                )
        );

        return jsonSerializer.serialize(domain);
    }
}
