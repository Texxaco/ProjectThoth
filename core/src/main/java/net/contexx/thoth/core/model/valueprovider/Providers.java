package net.contexx.thoth.core.model.valueprovider;

import net.contexx.thoth.core.model.phasea.variables.EntityIdentifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Providers<Identity> implements ProviderTag<Identity>{
    private final EntityIdentifier<Identity> entity;
    private final List<Provider<?, Identity>> providers;


    public Providers(EntityIdentifier<Identity> entity, Provider<?, Identity>...providers) {
        this.entity = entity;
        this.providers = Collections.unmodifiableList(Arrays.asList(providers));
    }

    public List<Provider<?, Identity>> getProvider() {
        return providers;
    }
}
