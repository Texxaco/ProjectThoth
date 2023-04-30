package net.contexx.thoth.core.persistence;

public interface ByteStorable<T> {
    byte[] serializeToBytes(T value);
    T deserializeFromBytes(byte[] data);
}
