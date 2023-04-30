package net.contexx.thoth.inttest;

import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.core.model.phasea.DestinationType;
import net.contexx.thoth.core.model.phasea.Symbol;
import net.contexx.thoth.core.model.phasea.variables.EntityIdentifier;
import net.contexx.thoth.core.model.valueprovider.Bridge;
import net.contexx.thoth.core.model.valueprovider.LambdaProvider;

import static net.contexx.thoth.core.model.common.DataType.*;

public interface IntTestDomain {

    EntityIdentifier<Integer> VAR_IDENT = new EntityIdentifier<>("Ident", NUMBER);

    DestinationType DEST_A = new DestinationType("Destination A");
    DestinationType DEST_B = new DestinationType("Destination B");

    Symbol SYMBOL_A = new Symbol("Symbol A");
    Symbol SYMBOL_B = new Symbol("Symbol B");

    Domain<Integer> INT_TEST_DOMAIN = new Domain<>("Int-Test-Domain", VAR_IDENT)
            .domainVariables()
            .destinations(
                    DEST_A, DEST_B
            )
            .symbols(
                    SYMBOL_A, SYMBOL_B
            ).providers(
                    new Bridge<Integer, String>("Another Domain", String::valueOf)
                            .add(new LambdaProvider<>("Sample Subdomain Provider", STRING, (s, c) -> s)),
                    new LambdaProvider<>("Sample Boolean Provider",BOOLEAN, (id, c) -> true),
                    new LambdaProvider<>("SampleData",STRING, ((id, c) -> "This is a sample data."))
            );
}
