package net.contexx.thoth.json.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractJsonConverter<T> extends StdSerializer<T> {

    protected AbstractJsonConverter(Class<T> t) {
        super(t);
    }

    protected AbstractJsonConverter(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    public abstract T load(JsonNode node, net.contexx.thoth.core.model.phasea.Domain<?> domain, int version) throws IOException;
}
