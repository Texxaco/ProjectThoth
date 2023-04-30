package net.contexx.thoth.core.model.phased;

import net.contexx.thoth.core.model.common.Sticky;

import java.util.Set;
import java.util.UUID;

public class Envelopes extends Sticky {

    public Envelopes(UUID id, Set<Envelop> envelopSet) {
        //todo
        this.id = id;
        this.envelopSet = envelopSet;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // id

    private final UUID id;

    public UUID getId() {
        return id;
    }

    //_____________________________________________________
    // envelopes

    private final Set<Envelop> envelopSet;

    public Set<Envelop> getEnvelopSet() {
        return envelopSet;
    }
}
