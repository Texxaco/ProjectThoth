package net.contexx.thoth.core.model.phasec;

import net.contexx.thoth.core.model.common.Sticky;
import net.contexx.thoth.core.model.phasea.Variable;
import net.contexx.thoth.core.model.phasea.Domain;

public class ExecutionContext<IdentityType> extends Sticky implements net.contexx.thoth.core.model.valueprovider.ExecutionContext {
    public ExecutionContext(Domain<IdentityType> domain, Variables variables) {
        this.domain = domain;
        this.variables = variables;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // functions

    public IdentityType getIdentity() {
        return getVariables().get(getDomain().getIdentifierVariable());
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // interface net.contexx.thoth.core.model.valueprovider.ExecutionContext

    @Override
    public <T> T getValue(Variable<T> variable) {
        return variables.get(variable);
    }


    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // domain

    private final Domain<IdentityType> domain;

    public Domain<IdentityType> getDomain() {
        return domain;
    }


    //_____________________________________________________
    // variables

    private final Variables variables;

    public Variables getVariables() {
        return variables;
    }
}
