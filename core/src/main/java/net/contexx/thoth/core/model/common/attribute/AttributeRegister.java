package net.contexx.thoth.core.model.common.attribute;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class AttributeRegister {

    private static final Map<AttribIdent, Attribute<?>> register = new HashMap<>();

    static void add(Attribute attribute) {
        register.put(new AttribIdent(attribute.getModule(), attribute.getName()), attribute);
    }

    public static Optional<Attribute<?>> find(String module, String name) {
        return Optional.ofNullable(register.get(new AttribIdent(module, name)));
    }

    private static class AttribIdent {
        String module;
        String name;

        public AttribIdent(String module, String name) {
            this.module = module;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AttribIdent that = (AttribIdent) o;
            return module.equals(that.module) && name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(module, name);
        }
    }
}
