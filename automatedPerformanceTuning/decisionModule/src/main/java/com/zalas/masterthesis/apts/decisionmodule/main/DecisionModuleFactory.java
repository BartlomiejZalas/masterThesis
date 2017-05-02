package com.zalas.masterthesis.apts.decisionmodule.main;

import com.zalas.masterthesis.apts.decisionmodule.impl.RuleBasedEngine;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.batch.EnableBatchRule;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.cache.DisableCacheRule;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.cache.EnableCacheRule;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.threads.SwitchTo20ThreadsRule;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.threads.SwitchTo50ThreadsRule;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.threads.SwitchTo80ThreadsRule;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.utils.CpuUsageInfluxDbClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;

public class DecisionModuleFactory {
    public DecisionModule create() {
        RuleBasedEngine ruleBasedEngine = new RuleBasedEngine();
        ConfigurationClient configurationClient = new ConfigurationClient();
        CpuUsageInfluxDbClient cpuUsageInfluxDbClient = new CpuUsageInfluxDbClient();

        ruleBasedEngine.register(new EnableCacheRule(configurationClient));
        ruleBasedEngine.register(new DisableCacheRule(configurationClient));
        ruleBasedEngine.register(new EnableBatchRule(configurationClient));
        ruleBasedEngine.register(new DisableCacheRule(configurationClient));
        ruleBasedEngine.register(new SwitchTo20ThreadsRule(configurationClient, cpuUsageInfluxDbClient));
        ruleBasedEngine.register(new SwitchTo50ThreadsRule(configurationClient, cpuUsageInfluxDbClient));
        ruleBasedEngine.register(new SwitchTo80ThreadsRule(configurationClient, cpuUsageInfluxDbClient));

        return new DecisionModule(ruleBasedEngine);
    }
}
