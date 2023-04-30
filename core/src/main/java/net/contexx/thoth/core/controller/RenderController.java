package net.contexx.thoth.core.controller;

import net.contexx.thoth.core.controller.RenderEngine.RenderException;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.core.model.phasec.Document;
import net.contexx.thoth.core.model.phasec.Folder;
import net.contexx.thoth.core.model.phasec.Variables;
import net.contexx.thoth.core.model.phased.Envelop;
import net.contexx.thoth.core.model.phased.Envelopes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.contexx.thoth.core.model.common.attribute.modules.DefaultAttributes.ACTIVE;

public class RenderController {

    private final RenderEngine renderEngine;

    public RenderController(RenderEngine renderEngine) { //todo get renderEngine from Register
        this.renderEngine = renderEngine;
    }

    public <IdentityType> Envelopes render(Domain<IdentityType> domain, IdentityType ident, Folder folder, Variables variables) {
        final Set<Document> documents = folder.getDocuments();

        final List<Document> toBeRendered = folder.getDocuments().parallelStream().filter(document -> (!document.hasAttribute(ACTIVE)) || document.getValue(ACTIVE)).collect(Collectors.toList());

        final Set<Envelop> envelopSet = new HashSet<>(documents.size());

        for (Document document : toBeRendered) {
            envelopSet.add(this.render(domain, ident, document, variables));
        }

        return new Envelopes(UUID.randomUUID(), envelopSet);
    }

    public <IdentityType> Envelop render(Domain<IdentityType> domain, IdentityType ident, Document document, Variables variables) {
        try {
            return new Envelop(
                    UUID.randomUUID(),
                    document.getAddressee(),
                    renderEngine.render(document.getRenderInfo(), domain, ident, document, document.getAddressee(), variables) //todo handle Exception
            );
        } catch (RenderException e) {
            throw new RuntimeException(e); //todo Exception richtig handlen
        }
    }
}
