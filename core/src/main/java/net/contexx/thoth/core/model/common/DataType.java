package net.contexx.thoth.core.model.common;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public final class DataType<Type> {
    private static final Set<DataType<?>> knownDataTypes = new HashSet<>();

    public static final DataType<String> STRING = new DataType<>("STRING", String.class, s -> s, s -> s, s -> s);
    public static final DataType<Integer> NUMBER = new DataType<>("NUMBER", Integer.class, String::valueOf, Integer::valueOf, i -> i);
    public static final DataType<Boolean> BOOLEAN = new DataType<>("BOOLEAN", Boolean.class, String::valueOf, Boolean::valueOf, b -> b);
    public static final DataType<Instant> INSTANT = new DataType<>("INSTANT", Instant.class, instant -> ISO_DATE_TIME.format(instant.atZone(ZoneId.systemDefault())), s -> Instant.from(ISO_DATE_TIME.parse(s)), i -> i);
    public static final DataType<UUID> UUID = new DataType<>("UUID", java.util.UUID.class, java.util.UUID::toString, java.util.UUID::fromString, u -> u);
    public static final DataType<Object> UNKNOWN = new DataType<>("UNKOWN", Object.class, o -> "", s -> null, null);



    private DataType(String name, Class<Type> datatypeClass, Function<Type, String> toStringFunction, Function<String, Type> fromStringFunction, Function<Type, Type> copyFunction) {
        this.name = name;
        this.datatypeClass = datatypeClass;
        this.toStringFunction = toStringFunction;
        this.fromStringFunction = fromStringFunction;
        this.copyFunction = copyFunction;
        knownDataTypes.add(this);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //
    private final Class<Type> datatypeClass;
    public Class<?> getDatatypeClass() {
        return datatypeClass;
    }

    //_____________________________________________________
    //
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //

    public static Optional<DataType<?>> find(Class<?> clazz) {
        return knownDataTypes.stream()
                .filter(d -> d != UNKNOWN)
                .filter(dataType -> dataType.getDatatypeClass().isAssignableFrom(clazz))
                .findFirst();
    }

    public static Optional<DataType<?>> find(String name) {
        return knownDataTypes.stream()
                .filter(d -> d != UNKNOWN)
                .filter(dataType -> dataType.getName().equals(name))
                .findFirst();
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // converters

    private final Function<Type, String> toStringFunction;
    private final Function<String, Type> fromStringFunction;

    public String toString(Type value) {
        return toStringFunction.apply(value);
    }

    public Type valueOf(String data) {
        return fromStringFunction.apply(data);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // copy

    private final Function<Type, Type> copyFunction;

    public Type copy(Type data) {
        return copyFunction.apply(data);
    }
}
