package net.contexx.thoth.core.model.phasea;

import net.contexx.thoth.core.model.common.DataType;
import net.contexx.thoth.core.model.phasea.variables.EntityIdentifier;
import net.contexx.thoth.core.model.phasea.variables.PersistantVariable;

import java.util.UUID;

import static net.contexx.thoth.core.model.common.DataType.STRING;

public final class TestDomain {
    public static final Variable<String> SYMBOL_A_VAR_1 = new PersistantVariable<>("SYMBOL_A_VAR_1", STRING);
    public static final Variable<String> SYMBOL_A_VAR_2 = new PersistantVariable<>("SYMBOL_A_VAR_2", STRING);
    public static final Variable<String> SYMBOL_A_VAR_3 = new PersistantVariable<>("SYMBOL_A_VAR_3", STRING);
    public static final Variable<String> SYMBOL_A_VAR_4 = new PersistantVariable<>("SYMBOL_A_VAR_4", STRING);

    public static final Symbol SYMBOL_A = new Symbol("Symbol A")
            .variables(
                    SYMBOL_A_VAR_1,
                    SYMBOL_A_VAR_2,
                    SYMBOL_A_VAR_3,
                    SYMBOL_A_VAR_4
            );

    public static final DestinationType PERSON_GROUP_1 = new DestinationType("Person Group 1");
    public static final DestinationType PERSON_GROUP_2 = new DestinationType("Person Group 2");

    public static final EntityIdentifier<UUID> ENTITY_IDENTIFIER = new EntityIdentifier<>("id", DataType.UUID);

    public static final Domain<UUID> TEST_DOMAIN = new Domain("Test Domain", ENTITY_IDENTIFIER)
            .symbols(SYMBOL_A)
            .destinations(PERSON_GROUP_1, PERSON_GROUP_2)
//            .domainVariables(ENTITY_IDENTIFIER)
            ;

    private TestDomain() { }
}
