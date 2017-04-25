package com.zalas.masterthesis.apts.decisionmodule.main;

import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.apts.decisionmodule.impl.RuleBasedEngine;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.DummyRule;

public class DecisionModuleFactory {
    public DecisionModule create() {
        RuleBasedEngine ruleBasedEngine = new RuleBasedEngine();
        ruleBasedEngine.register(new DummyRule());

        return new DecisionModule(ruleBasedEngine);
    }
}
