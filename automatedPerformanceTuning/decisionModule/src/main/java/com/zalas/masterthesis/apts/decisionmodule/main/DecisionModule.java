package com.zalas.masterthesis.apts.decisionmodule.main;

import com.zalas.masterthesis.apts.decisionmodule.api.PerformanceIssue;
import com.zalas.masterthesis.apts.decisionmodule.impl.AptsRuleBasedEngine;

public class DecisionModule {
    private AptsRuleBasedEngine aptsRuleBasedEngine;

    public DecisionModule(AptsRuleBasedEngine aptsRuleBasedEngine) {

        this.aptsRuleBasedEngine = aptsRuleBasedEngine;
    }

    public void performDecision(PerformanceIssue performanceIssue) {
        aptsRuleBasedEngine.getBestRule(performanceIssue);
    }
}
