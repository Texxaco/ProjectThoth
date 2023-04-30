package net.contexx.thoth.ruleengine.homegrow.model;

import net.contexx.thoth.core.model.common.DataType;
import net.contexx.thoth.core.model.common.attribute.Attributable;
import net.contexx.thoth.core.model.phasec.ExecutionContext;
import net.contexx.thoth.core.model.valueprovider.Provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class Condition<T, IdentityType> implements Rule<IdentityType> {
    public Condition(DataType<T> dataType, Provider<T, IdentityType> provider, Operator<T> operator, List<T> shoulds) {
        this.dataType = dataType;
        this.provider = provider;
        this.operator = operator;
        this.shoulds.addAll(shoulds);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // datatype

    private final DataType<T> dataType;

    public DataType<T> getDataType() {
        return dataType;
    }

    //_____________________________________________________
    // provider


    private Provider<T, IdentityType> provider;

    public Provider<T, IdentityType> getProvider() {
        return provider;
    }

    public void setProvider(Provider<T, IdentityType> provider) {
        this.provider = provider;
    }

    //_____________________________________________________
    // operator


    private Operator<T> operator;

    public Operator<T> getOperator() {
        return operator;
    }

    public void setOperator(Operator<T> operator) {
        this.operator = operator;
    }

    //_____________________________________________________
    // List of shoulds

    private final List<T> shoulds = new ArrayList<>();

    public List<T> getShoulds() {
        return Collections.unmodifiableList(shoulds);
    }

    public void addShould(T should) {
        this.shoulds.add(should);
    }

    public void removeShould(T should) {
        this.shoulds.remove(should);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // evaluate functionality

    @Override
    public EvaluationResult<IdentityType> evaluate(Attributable attributable, ExecutionContext<IdentityType> context) {
        final T given = provider.getValue(context.getIdentity(), context); //todo cachen

        return new ConditionEvalResult<>(this, getProvider(), given, operator, shoulds, shoulds.parallelStream().anyMatch(should -> operator.check(given, should)));
    }

    private static class ConditionEvalResult<T, IdentityType> extends EvaluationResult<IdentityType> {

        private Provider<T, ?> provider;
        private T given;
        private Operator<T> operator;
        private List<T> should;

        public ConditionEvalResult(Rule<IdentityType> parent, Provider<T, ?> provider, T given, Operator<T> operator, List<T> should, boolean result) {
            super(parent, result);
            this.provider = provider;
            this.given = given;
            this.should = should;
            this.operator = operator;
        }

        @Override
        public void prittyPrint(StringBuilder output, String prefix) {
            final String prittyPrintShould;
            final int size = should.size();
            if(size == 0) {
                prittyPrintShould = "<empty>";
            } else if(size == 1) {
                prittyPrintShould = "\""+provider.getDatatype().toString(should.get(0))+"\"";
            } else {
                final StringJoiner stringJoiner = new StringJoiner("\",\"", "[\"", "\"]");
                should.stream().map(s -> provider.getDatatype().toString(s)).forEachOrdered(stringJoiner::add);
                prittyPrintShould = stringJoiner.toString();
            }

            output.append(prefix).append(provider.getName())
                    .append(" -> ").append(isAllowed() ? "true ": "false")
                    .append(" -> given: \"").append(provider.getDatatype().toString(given)).append("\" ")
                    .append(operator.getName())
                    .append(" should: ").append(prittyPrintShould);
        }
    }
}
