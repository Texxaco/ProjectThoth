package net.contexx.thoth.json.phaseb;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import net.contexx.thoth.core.model.phaseb.RuleSet;
import net.contexx.thoth.json.phaseb.plugins.PluginRegister;
import net.contexx.thoth.json.phaseb.plugins.RuleEnginePlugin;

import java.io.IOException;
import java.util.Optional;

public class RuleSetAdapter {

    private static final String META = "ruleEngineMetaData";
    private static final String META_ENGINE_NAME = "engineName";
    private static final String META_DATA_VERSION = "dataVersion";
    private static final String RULESET = "ruleSet";

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //RuleSetAdapter implementation

    public static void serialize(JsonGenerator gen, RuleSet ruleSet) throws IOException {
        if(ruleSet == null) return;

        final RuleEnginePlugin<RuleSet> ruleEnginePlugin = PluginRegister.findRuleEnginePlugin(ruleSet).orElseThrow(); //todo passende Exception eintragen.

        //todo
        gen.writeObjectFieldStart(META);
            gen.writeStringField(META_ENGINE_NAME, ruleEnginePlugin.getRuleEngineName());
            gen.writeNumberField(META_DATA_VERSION, ruleEnginePlugin.getVersion());
        gen.writeEndObject();

        gen.writeObjectField(RULESET, ruleSet);
    }

    public static Optional<RuleSet> load(JsonNode parentNode, net.contexx.thoth.core.model.phasea.Domain<?> domain) throws IOException {
        final JsonNode ruleSet = parentNode.get(RULESET);
        if(ruleSet == null) return Optional.empty();

        final JsonNode meta = parentNode.get(META);
        if(meta == null) throw new IOException("Missing RuleSet-Metainformation-Block in:\n"+parentNode.toPrettyString());

        final String ruleEngineName = meta.get("engineName").asText(); //todo null prüfung?
        final int ruleSetDataVersion = meta.get("dataVersion").asInt(); //todo null prüfung?

        final Optional<RuleEnginePlugin<RuleSet>> ruleEnginePlugin = PluginRegister.getRuleEnginePlugin(ruleEngineName);

        if(ruleEnginePlugin.isPresent()) {
            return ruleEnginePlugin.get().load(ruleSet, domain, ruleSetDataVersion);
        } else throw new IOException(String.format("Rule Engine with name '%s' not found.", ruleEngineName));
    }
}
