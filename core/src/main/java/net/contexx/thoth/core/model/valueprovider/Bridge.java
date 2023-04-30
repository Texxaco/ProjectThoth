package net.contexx.thoth.core.model.valueprovider;

import net.contexx.thoth.core.model.phasea.Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Bridge<OriginIdentity, BridgedIdentity> implements ProviderTag<OriginIdentity> {

    private final Function<OriginIdentity, BridgedIdentity> bridgeFunction;

    public Bridge(String bridgedDomainName, Function<OriginIdentity, BridgedIdentity> bridgeFunction) {
        this.bridgedDomainName = bridgedDomainName;
        this.bridgeFunction = bridgeFunction;
    }

    public Bridge(Domain<BridgedIdentity> bridgedDomain, Function<OriginIdentity, BridgedIdentity> bridgeFunction) {
        this(bridgedDomain.getName(), bridgeFunction);

    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // adds & bridging

    public Bridge<OriginIdentity, BridgedIdentity> add(Providers<BridgedIdentity> providerContainer) {
        providerContainer.getProvider().stream().map(provider -> new BridgeProvider<>(getBridgedDomainName(), provider, bridgeFunction)).forEach(providers::add);
        return this;
    }

    public Bridge<OriginIdentity, BridgedIdentity> add(Provider<?, BridgedIdentity> provider) {
        providers.add(new BridgeProvider<>(getBridgedDomainName(), provider, bridgeFunction));
        return this;
    }

    public Bridge<OriginIdentity, BridgedIdentity> add(Bridge<BridgedIdentity, ?> bridge) {
        bridge.getProvider().stream().map(provider -> new BridgeProvider<>(getBridgedDomainName(), provider, bridgeFunction)).forEach(providers::add);
        return this;
    }

    private static class BridgeProvider<T, OriginIdentity, BridgedIdentity> extends Provider<T, OriginIdentity> {
        private final Provider<T, BridgedIdentity> bridgedProvider;
        private final Function<OriginIdentity, BridgedIdentity> bridgeFunction1;

        public BridgeProvider(String bridgedDomainName, Provider<T, BridgedIdentity> bridgedProvider, Function<OriginIdentity, BridgedIdentity> bridgeFunction) {
            super(bridgedDomainName+"->"+bridgedProvider.getName(), bridgedProvider.getDatatype());
            this.bridgedProvider = bridgedProvider;
            bridgeFunction1 = bridgeFunction;
        }

        @Override
        public T getValue(OriginIdentity originIdentity, ExecutionContext executionContext) {
            return bridgedProvider.getValue(bridgeFunction1.apply(originIdentity), executionContext);
        }
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // providers

    private final List<Provider<?, OriginIdentity>> providers = new ArrayList<>();

    public List<Provider<?, OriginIdentity>> getProvider() {
        return Collections.unmodifiableList(providers);
    }

    //_____________________________________________________
    //

    private final String bridgedDomainName;

    public String getBridgedDomainName() {
        return bridgedDomainName;
    }
}
