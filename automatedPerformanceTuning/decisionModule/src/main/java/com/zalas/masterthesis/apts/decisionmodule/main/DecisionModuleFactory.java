package com.zalas.masterthesis.apts.decisionmodule.main;

import com.zalas.masterthesis.apts.decisionmodule.impl.RuleBasedEngine;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.EnableCacheRule;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;

public class DecisionModuleFactory {
    public DecisionModule create() {
        RuleBasedEngine ruleBasedEngine = new RuleBasedEngine();
        ConfigurationClient configurationClient = new ConfigurationClient();
        ruleBasedEngine.register(new EnableCacheRule(configurationClient));

        return new DecisionModule(ruleBasedEngine);
    }
}
