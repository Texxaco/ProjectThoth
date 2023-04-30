package net.contexx.thoth.core.model.phasea;

import net.contexx.thoth.core.model.common.Sticky;
import net.contexx.thoth.core.model.phasea.variables.EntityIdentifier;
import net.contexx.thoth.core.model.valueprovider.Bridge;
import net.contexx.thoth.core.model.valueprovider.Provider;
import net.contexx.thoth.core.model.valueprovider.ProviderTag;
import net.contexx.thoth.core.model.valueprovider.Providers;

import java.util.*;

public class Domain<IdentityType> extends Sticky implements net.contexx.thoth.core.model.common.Domain  { //TODO maybe sometimes in the future seal this class to ExtendableDomain

    public Domain(String name, EntityIdentifier<IdentityType> mainEntityIdentifier) {
        this.name = name;
        this.identifierVariable = mainEntityIdentifier;
        register.put(name, this);
        variables.add(mainEntityIdentifier);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // name
    private final String name;

    @Override
    public String getName() {
        return name;
    }

    //_____________________________________________________
    //

    private final EntityIdentifier<IdentityType> identifierVariable;

    public EntityIdentifier<IdentityType> getIdentifierVariable() {
        return identifierVariable;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // destinations

    protected final Set<DestinationType> destinations = new HashSet<>();

    public Domain<IdentityType> destinations(DestinationType...destinations) {
        for (DestinationType d : destinations) {
            d.setDomain(this);
            this.destinations.add(d);
        }
        return this;
    }

    public Set<DestinationType> getDestinations() {
        return Collections.unmodifiableSet(destinations);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // symbols

    protected final Set<Symbol> symbols = new HashSet<>();

    public Domain<IdentityType> symbols(Symbol...symbols){
        for (Symbol s : symbols) {
            s.setDomain(this);
            this.symbols.add(s);
        }
        return this;
    }

    public Set<Symbol> getSymbols() {
        return Collections.unmodifiableSet(symbols);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //domain variables

    protected final Set<Variable<?>> variables = new HashSet<>();

    public Domain<IdentityType> domainVariables(Variable<?>...variables){
        for (Variable<?> v : variables) {
            v.setDomain(this);
            this.variables.add(v);
        }
        return this;
    }

    public Set<Variable<?>> getVariables() {
        return Collections.unmodifiableSet(variables);
    }


    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // domain register

    private static final Map<String, Domain<?>> register = new HashMap<>();

    public static Optional<Domain<?>> findDomain(String name) {
        return Optional.ofNullable(register.get(name));
    }


    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // provider

    private final Map<String, Provider<?, IdentityType>> providers = new HashMap<>();

    @SafeVarargs
    public final Domain<IdentityType> providers(final ProviderTag<IdentityType>... providers) {
        for (ProviderTag<IdentityType> elem : providers) {
            if(elem instanceof Provider<?, IdentityType> provider) {
                addProvider(provider);
            } else if(elem instanceof Providers<IdentityType> providerContainer) {
                for (Provider<?, IdentityType> provider : providerContainer.getProvider()) {
                    addProvider(provider);
                }
            } else if(elem instanceof Bridge<IdentityType, ?> providerContainer) {
                for (Provider<?, IdentityType> provider : providerContainer.getProvider()) {
                    addProvider(provider);
                }
            }
        }

        return this;
    }

    private void addProvider(Provider<?, IdentityType> provider) {
        assert !this.providers.containsKey(provider.getName()) : "Non uniqe provider name '"+ provider.getName()+"' found when creating domain '"+getName()+"'";
        this.providers.put(provider.getName(), provider);
    }

    public Collection<Provider<?, IdentityType>> getProvider() {
        return Collections.unmodifiableCollection(providers.values());
    }

    public Optional<Provider<?, IdentityType>> findProvider(String name) {
        return Optional.ofNullable(providers.get(name));
    }
}
