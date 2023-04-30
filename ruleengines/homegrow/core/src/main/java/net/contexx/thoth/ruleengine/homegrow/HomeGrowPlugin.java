package net.contexx.thoth.ruleengine.homegrow;

import net.contexx.thoth.core.controller.RuleEngine;
import net.contexx.thoth.core.plugins.RuleEnginePlugin;

public class HomeGrowPlugin implements RuleEnginePlugin {

    public RuleEngine createRuleEngine() {
        return new HomegrowRuleEngine();
    }

    //todo persistence hook
}
