package net.contexx.thoth.ruleengine.homegrow.model;

import java.util.function.BiFunction;

public class Operator<T> {
    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // constructor

    protected Operator(String name, BiFunction<T, T, Boolean> checkFunction) {
        this.name = name;
        this.checkFunction = checkFunction;
        Operators.put(this);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // name

    private final String name;

    public String getName() {
        return name;
    }

    //_____________________________________________________
    // check

    private final BiFunction<T, T, Boolean> checkFunction;

    public boolean check(T given, T should) {
        return checkFunction.apply(given, should);
    }


}
