package com.zalas.masterthesis.apts.decisionmodule.main;

import com.zalas.masterthesis.apts.decisionmodule.impl.RuleBasedEngine;

public class DecisionModuleFactory {
    public DecisionModule create() {
        RuleBasedEngine ruleBasedEngine = new RuleBasedEngine();
        ruleBasedEngine.register(null);

        return new DecisionModule(ruleBasedEngine);
    }
}
