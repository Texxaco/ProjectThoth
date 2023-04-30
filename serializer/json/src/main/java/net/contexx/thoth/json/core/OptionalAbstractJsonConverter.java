package net.contexx.thoth.json.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class OptionalAbstractJsonConverter<T> extends StdSerializer<T> {

    public OptionalAbstractJsonConverter(Class<T> t) {
        super(t);
    }

    public OptionalAbstractJsonConverter(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    public abstract Optional<T> load(JsonNode node, net.contexx.thoth.core.model.phasea.Domain<?> domain, int version) throws IOException;
}
