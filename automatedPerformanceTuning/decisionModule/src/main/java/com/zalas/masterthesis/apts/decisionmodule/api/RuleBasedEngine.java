package com.zalas.masterthesis.apts.decisionmodule.api;

import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.DummyRule;

public interface RuleBasedEngine {
    Rule getBestRule(PerformanceIssue performanceIssue);
    void register(Rule rule);
}
