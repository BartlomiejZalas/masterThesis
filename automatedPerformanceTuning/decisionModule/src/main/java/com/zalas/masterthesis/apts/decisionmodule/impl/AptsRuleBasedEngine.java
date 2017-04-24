package com.zalas.masterthesis.apts.decisionmodule.impl;

import com.zalas.masterthesis.apts.decisionmodule.api.PerformanceIssue;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.apts.decisionmodule.api.RuleBasedEngine;

import java.util.HashSet;
import java.util.Set;

public class AptsRuleBasedEngine implements RuleBasedEngine {

    private Set<Rule> rules = new HashSet<>();

    @Override
    public Rule getBestRule(PerformanceIssue performanceIssue) {
        return null;
    }

    @Override
    public void register(Rule rule) {
        rules.add(rule);
    }
}
