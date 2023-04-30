package net.contexx.thoth.core.model.phaseb;

import net.contexx.thoth.core.model.common.attribute.Attributable;
import net.contexx.thoth.core.model.common.attribute.AttributeValue;
import net.contexx.thoth.core.model.phasea.DestinationType;

import java.util.Set;

public class Destination extends Attributable {
    public Destination(DestinationType type, Set<AttributeValue<?>> attributeValues) {
        super(attributeValues);
        this.type = type;
    }

    public Destination(DestinationType type) {
        this(type, null);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // type

    private DestinationType type;

    public DestinationType getType() {
        return type;
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

}
