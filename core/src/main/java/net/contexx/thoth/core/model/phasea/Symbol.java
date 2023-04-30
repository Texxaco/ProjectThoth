package net.contexx.thoth.core.model.phasea;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Symbol extends AbstractEntity {

    public Symbol(String name) {
        this.name = name;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    private final String name;

    public String getName() {
        return name;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //variables

    private final Set<Variable<?>> variables = new HashSet<>();

    @SuppressWarnings("UseBulkOperation")
    public Symbol variables(Variable<?>...variables){
        Arrays.stream(variables).forEach(this.variables::add);
        return this;
    }

    public Set<Variable<?>> getVariables() {
        return Collections.unmodifiableSet(variables);
    }
}
