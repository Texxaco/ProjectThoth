package net.contexx.thoth.core.model.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public abstract class Sticky {
    @SuppressWarnings("rawtypes")
    private final Map<Key,Object> store = new ConcurrentHashMap<>();

    public <K, V extends K> K stick(Key<K> key, V value) {
        store.put(key, value);
        return value;
    }

    public <K> K get(Key<K> key) {
        return getOrDefault(key, null);
    }

    @SuppressWarnings("unchecked")
    public <K> K getOrDefault(Key<K> key, K defaultValue) {
        return (K) store.getOrDefault(key, defaultValue);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <K> K stickIfAbsent(Key<K> key, Function<? super Key<K>, K> constructor) {
        return (K) store.computeIfAbsent(key, (Function<? super Key, ?>) constructor);
    }

    public boolean isSticked(Key<?> key) {
        return store.containsKey(key);
    }

    public boolean remove(Key<?> key) {
        return store.remove(key) != null;
    }
}
