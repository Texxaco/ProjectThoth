package net.contexx.thoth.renderer.xmlfo;

import net.contexx.thoth.core.model.phasec.ExecutionContext;
import net.contexx.thoth.core.model.valueprovider.Provider;

public class DataProvider<IdentyType> {

    private final ExecutionContext<IdentyType> context;

    public DataProvider(ExecutionContext<IdentyType> context) {
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    public <T> String from(String name) {

        final Provider<T, IdentyType> provider = (Provider<T, IdentyType>) context.getDomain().findProvider(name).orElseThrow(); //todo throw adequate Exception

        return provider.getDatatype().toString(provider.getValue(context.getIdentity(), context));
    }

}
