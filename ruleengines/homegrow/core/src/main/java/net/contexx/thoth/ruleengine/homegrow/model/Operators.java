package net.contexx.thoth.ruleengine.homegrow.model;

import java.util.*;
import java.util.regex.Pattern;

public class Operators {

    private static final Map<String, Operator<?>> operators = new HashMap<>();

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // operators

    public static final Operator<Object> EQUALS = new Operator<>("EQUALS", Operators::equalX);

    public static boolean equalX(Object a, Object b) {
        return Objects.equals(a, b);
    }
    public static final Operator<Object> SAME = new Operator<>("SAME", (given, should) -> given == should);
    public static final Operator<Comparable> GREATER_THEN = new Operator<>("GREATER_THEN", (given, should) -> given.compareTo(should) > 0 );
    public static final Operator<Comparable> LOWER_THEN = new Operator<>("LOWER_THEN", (given, should) -> given.compareTo(should) < 0);

    public static final Operator<String> REGEXP = new RegExpOperator("REGEXP");

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // operator lists

    public static Optional<Operator<?>> find(String name) {
        return Optional.ofNullable(operators.get(name));
    }

    static void put(Operator<?> operator) {
        operators.put(operator.getName(), operator);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // custom implementations

    private static class RegExpOperator extends Operator<String> {
        private static final Map<String, Pattern> patternCache = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Pattern> eldest) {
                return size() > 200;
            }
        };

        protected RegExpOperator(String name) {
            super(name, null);
        }

        @Override
        public boolean check(String given, String should) {
            final Pattern pattern = patternCache.computeIfAbsent(should, Pattern::compile);
            return pattern.matcher(given).matches();
        }
    }
}
