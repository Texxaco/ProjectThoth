package net.contexx.thoth.core.model.phasea;

import net.contexx.thoth.core.model.phasea.variables.EntityIdentifier;

public final class ExtendableDomain extends Domain {
    public ExtendableDomain(String name, EntityIdentifier mainEntityIdentifier) {
        super(name, mainEntityIdentifier);
    }

    public void register(DestinationType destinationType) {
        destinationType.setDomain(this);
        destinations.add(destinationType);
    }

    public void register(Symbol symbol) {
        symbol.setDomain(this);
        symbols.add(symbol);
    }

    public void register(Variable<?> variable) {
        variable.setDomain(this);
        variables.add(variable);
    }
}
