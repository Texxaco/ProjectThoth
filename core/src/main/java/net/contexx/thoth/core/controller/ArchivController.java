package net.contexx.thoth.core.controller;

import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.core.model.phased.Envelop;
import net.contexx.thoth.core.model.phased.Envelopes;

import java.util.Collections;
import java.util.List;

public class ArchivController {

    private final Archiv archiv;

    public ArchivController(Archiv archiv) {
        this.archiv = archiv;
    }

    public <IdentityType> void archiv(Domain<IdentityType> domain, IdentityType ident, Envelopes envelopes) {
        //todo
    }

    public <IdentityType> void archiv(Domain<IdentityType> domain, IdentityType ident, Envelop envelop) {
        //todo
    }

    public <IdentityType> void markAsSend(Domain<IdentityType> domain, IdentityType ident, Envelopes envelopes) {
        //todo
    }

    public <IdentityType> void markAsSend(Domain<IdentityType> domain, IdentityType ident, Envelop envelop) {
        //todo
    }

    public <IdentityType> List<Envelop> getEnvelopes(Domain<IdentityType> domain, IdentityType ident) {
        //todo
        return Collections.emptyList();
    }
}
