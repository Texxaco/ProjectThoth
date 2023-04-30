package net.contexx.thoth.core.persistence;

import static java.nio.charset.StandardCharsets.UTF_8;

public interface Storable<T> extends ByteStorable<T> {

    String serialize(T value);
    T deserialize(String data);

    @Override
    default byte[] serializeToBytes(T value) {
        return serialize(value).getBytes(UTF_8);
    }

    @Override
    default T deserializeFromBytes(byte[] data) {
        return deserialize(new String(data, UTF_8));
    }
}
