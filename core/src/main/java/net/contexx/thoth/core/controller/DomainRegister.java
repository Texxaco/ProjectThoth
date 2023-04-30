package net.contexx.thoth.core.controller;

import net.contexx.thoth.core.model.phaseb.Domain;

import java.util.IdentityHashMap;
import java.util.Map;

public class DomainRegister {
    private final Map<net.contexx.thoth.core.model.phasea.Domain<?>, Domain<?>> domains = new IdentityHashMap<>();

    //todo Lademechanismus implementieren

    @SuppressWarnings("unchecked")
    public <IdentityType> Domain<IdentityType> getDomain(net.contexx.thoth.core.model.phasea.Domain<IdentityType> domain) {
        return (Domain<IdentityType>) domains.get(domain);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public void register(Domain<?> domain) throws DuplicateRegisterException {
        if(domains.containsKey(domain)) throw new DuplicateRegisterException(domain.getOriginDomain());

        domains.put(domain.getOriginDomain(), domain);
    }

    private static class DuplicateRegisterException extends Exception {
        public DuplicateRegisterException(net.contexx.thoth.core.model.phasea.Domain<?> domain) {
            super("Unable register domain '"+domain.getName()+"', cause domain is already registered"); //todo internationalize
        }
    }
}
