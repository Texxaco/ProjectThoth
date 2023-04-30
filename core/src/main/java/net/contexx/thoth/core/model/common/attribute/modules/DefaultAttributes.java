package net.contexx.thoth.core.model.common.attribute.modules;

import net.contexx.thoth.core.model.common.attribute.Attribute;

import static net.contexx.thoth.core.model.common.DataType.BOOLEAN;

public interface DefaultAttributes {
    String MODULE_NAME = "Default";

    Attribute<Boolean> ACTIVE = new Attribute<>(MODULE_NAME, "active", BOOLEAN);
}
