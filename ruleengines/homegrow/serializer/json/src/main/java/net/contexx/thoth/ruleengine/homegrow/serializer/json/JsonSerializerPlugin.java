package net.contexx.thoth.ruleengine.homegrow.serializer.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.core.model.phaseb.RuleSet;
import net.contexx.thoth.json.phaseb.plugins.RuleEnginePlugin;
import net.contexx.thoth.ruleengine.homegrow.model.Rule;

import java.io.IOException;
import java.util.Optional;

public class JsonSerializerPlugin implements RuleEnginePlugin<Rule<?>> {
    private RuleJsonConverter<Rule<?>> ruleRuleJsonConverter = new RuleJsonConverter<>();

    @Override
    public String getRuleEngineName() {
        return "Homegrow";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public boolean supports(RuleSet ruleSet) {
        return Rule.class.isAssignableFrom(ruleSet.getClass());
    }

    @Override
    public Module getJacksonModule() {
        final SimpleModule module = new SimpleModule("json serializer homegrow");
        module.addSerializer(ruleRuleJsonConverter);
        return module;
    }

    @Override
    public Optional<Rule<?>> load(JsonNode node, Domain<?> domain, int version) throws IOException {
        return ruleRuleJsonConverter.load(node, domain, version);
    }
}
