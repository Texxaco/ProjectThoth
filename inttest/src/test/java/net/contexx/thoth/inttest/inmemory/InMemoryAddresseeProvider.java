package net.contexx.thoth.inttest.inmemory;

import net.contexx.thoth.core.controller.AddresseeProvider;
import net.contexx.thoth.core.model.phasea.DestinationType;
import net.contexx.thoth.core.model.phaseb.Destination;
import net.contexx.thoth.core.model.phasec.Addressee;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryAddresseeProvider implements AddresseeProvider {

    private Map<DestinationType, Set<Addressee>> store = new IdentityHashMap<>();

    public InMemoryAddresseeProvider add(DestinationType destination, String surname){
        store.computeIfAbsent(destination, d -> new HashSet<>()).add(new InMemoryAddressee(surname));
        return this;
    }

    @Override
    public Set<Addressee> resolve(Destination destination) {
        return store.get(destination.getType());
    }

    public static class InMemoryAddressee extends Addressee {
        private final String surname;

        public InMemoryAddressee(String surname) {
            this.surname = surname;
        }

        @Override
        public String toString() {
            return surname;
        }
    }
}
