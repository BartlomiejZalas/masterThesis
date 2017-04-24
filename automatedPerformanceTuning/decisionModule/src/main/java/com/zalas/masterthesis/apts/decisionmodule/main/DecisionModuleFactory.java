package com.zalas.masterthesis.apts.decisionmodule.main;

import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.apts.decisionmodule.impl.AptsRuleBasedEngine;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.DummyRule;

public class DecisionModuleFactory {
    public DecisionModule create() {
        AptsRuleBasedEngine aptsRuleBasedEngine = new AptsRuleBasedEngine();
        aptsRuleBasedEngine.register(new DummyRule());

        return new DecisionModule(aptsRuleBasedEngine);
    }
}
