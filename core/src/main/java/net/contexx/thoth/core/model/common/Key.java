package net.contexx.thoth.core.model.common;

public final class Key<K> {
    private final String name;

    public Key(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
