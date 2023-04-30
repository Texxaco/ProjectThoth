package net.contexx.thoth.core.model.valueprovider;

import net.contexx.thoth.core.model.common.DataType;

import java.util.function.BiFunction;

public class LambdaProvider<ResultType, IdentityType> extends Provider<ResultType, IdentityType> {

    private final BiFunction<IdentityType, ExecutionContext, ResultType> implementation;

    public LambdaProvider(String domainUniqueName, DataType<ResultType> datatype, BiFunction<IdentityType, ExecutionContext, ResultType> implementation) {
        super(domainUniqueName, datatype);
        this.implementation = implementation;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // value getter

    public ResultType getValue(IdentityType identity, ExecutionContext executionContext) {
        return implementation.apply(identity, executionContext);
    }
}
