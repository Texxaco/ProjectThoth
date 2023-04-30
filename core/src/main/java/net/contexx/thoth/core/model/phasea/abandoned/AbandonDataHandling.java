package net.contexx.thoth.core.model.phasea.abandoned;

import net.contexx.thoth.core.model.common.DataType;
import net.contexx.thoth.core.model.phasea.DestinationType;
import net.contexx.thoth.core.model.phasea.ExtendableDomain;
import net.contexx.thoth.core.model.phasea.Symbol;
import net.contexx.thoth.core.model.phasea.Variable;
import net.contexx.thoth.core.model.phasea.variables.EntityIdentifier;
import net.contexx.thoth.core.model.phasea.variables.PersistantVariable;

public final class AbandonDataHandling {

    private AbandonDataHandling() { /*no instanciation*/ }

    public static final ExtendableDomain ABANDONED_DOMAIN = new ExtendableDomain("Abandoned Domain", new EntityIdentifier<>("Abandoned Domain Identifier", DataType.UUID));

    public static DestinationType getAbandonedDestination(String name) {
        final DestinationType destinationType = new DestinationType(name);
        ABANDONED_DOMAIN.register(destinationType);
        return destinationType;
    }

    public static Symbol getAbandonedSymbol(String name) {
        final Symbol symbol = new Symbol(name);
        ABANDONED_DOMAIN.register(symbol);
        return symbol;
    }

    public static <T> Variable<T> getAbandonedVariable(String name, DataType<T> dataType) {
        final Variable<T> variable = new PersistantVariable<T>(name, dataType);
        ABANDONED_DOMAIN.register(variable);
        return variable;
    }
}
