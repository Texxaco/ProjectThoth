package net.contexx.thoth.core.persistence.phaceb;


import net.contexx.thoth.core.model.phaseb.Domain;

public interface Storage {
    void store(Domain domain);
    Domain load(net.contexx.thoth.core.model.phasea.Domain domain);
}
