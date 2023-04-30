package net.contexx.thoth.core.model.phaseb;

import net.contexx.thoth.core.controller.RenderEngine;
import net.contexx.thoth.core.model.common.attribute.Attributable;
import net.contexx.thoth.core.model.common.attribute.AttributeValue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Template extends Attributable {

    public Template(UUID identifier, String name, Set<Destination> destinations, RenderEngine.RenderInfo renderInfo, Set<AttributeValue<?>> attributeValues) {
        super(attributeValues);
        this.identifier = identifier;
        this.name = name;
        this.renderInfo = renderInfo;
        this.destinations.addAll(destinations);

    }

    public Template(UUID identifier, String name, Destination destination, RenderEngine.RenderInfo renderInfo) {
        this(identifier, name, Collections.singleton(destination), renderInfo, null);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // properties

    //_____________________________________________________
    // domain
    private Domain<?> domain;

    public Domain<?> getDomain() {
        return domain;
    }

    public <E> void setDomain(Domain<E> domain){
        assert domain != null : "domain could not be null";
        if(this.domain != null) throw new RuntimeException("TODO"); //todo Write the Exception message
        this.domain = domain;
    }

    //_____________________________________________________
    // identifier

    private UUID identifier;

    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    //_____________________________________________________
    // name

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //_____________________________________________________
    // destination

    private final Set<Destination> destinations = new HashSet<>();

    public Set<Destination> getDestinations() {
        return destinations;
    }

    //_____________________________________________________
    // RuleSet

    private RuleSet ruleSet;

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }

    //_____________________________________________________
    // rendererInfos

    private RenderEngine.RenderInfo renderInfo;

    public RenderEngine.RenderInfo getRendererInfo() {
        return renderInfo;
    }

    public void setRendererInfos(RenderEngine.RenderInfo renderInfo) {
        if(renderInfo == null) throw new RuntimeException("rendererInfos should not be null");
        this.renderInfo = renderInfo;
    }
}
