package net.contexx.thoth.json.phaseb.plugins;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.core.model.phaseb.RuleSet;

import java.io.IOException;
import java.util.Optional;

public interface RuleEnginePlugin<RS extends RuleSet> {
    String getRuleEngineName();

    int getVersion();

    boolean supports(RuleSet ruleSet);

    Module getJacksonModule();

    Optional<RS> load(JsonNode node, Domain<?> domain, int version) throws IOException;
}
