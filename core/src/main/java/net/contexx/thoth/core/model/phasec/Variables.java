package net.contexx.thoth.core.model.phasec;

import net.contexx.thoth.core.model.phasea.Variable;

import java.util.IdentityHashMap;
import java.util.Map;

public class Variables {
    private Map<Variable<?>, Object> variables = new IdentityHashMap<>();

    public <T> Variables add(Variable<T> variable, T value) {
        assert !variables.containsKey(variable) : "there is already a value this variable '"+variable.getName()+"'.\n" +
                                                   "Old value: '"+variables.get(variable)+"'\n" +
                                                   "New value: '"+value+"'\n";

        variables.put(variable, value);

        return this;
    }

    public Variables remove(Variable<?> variable) {
        variables.remove(variable);

        return this;
    }

    public <T> T get(Variable<T> variable) {
        return (T)variables.get(variable);
    }
}
