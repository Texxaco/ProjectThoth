package net.contexx.thoth.core.controller;

import net.contexx.thoth.core.model.phaseb.Destination;
import net.contexx.thoth.core.model.phasec.Addressee;

import java.util.Set;

public interface AddresseeProvider {
    Set<Addressee> resolve(Destination destination);
}
