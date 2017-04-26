package com.zalas.masterthesis.apts.decisionmodule.impl;

import com.zalas.masterthesis.apts.decisionmodule.api.PerformanceIssue;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.EmptyRule;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

import java.util.*;

import static com.google.common.collect.Iterables.getFirst;

public class RuleBasedEngine {

    private Set<Rule> rules = new HashSet<>();

    public void register(Rule rule) {
        rules.add(rule);
    }

    public Rule getBestRule(PerformanceIssue performanceIssue, ApplicationConfiguration applicationConfiguration) {
        List<Rule> applicableRules = getApplicableRules(performanceIssue, applicationConfiguration);

        applicableRules.sort(new RuleComparatorFromHighToLow());

        return getFirst(applicableRules, EmptyRule.getInstance());
    }

    private List<Rule> getApplicableRules(PerformanceIssue performanceIssue, ApplicationConfiguration applicationConfiguration) {
        List<Rule> applicableRules = new ArrayList<>();
        for (Rule rule : rules) {
            if (rule.isRuleApplicable(performanceIssue, applicationConfiguration)) {
                applicableRules.add(rule);
            }
        }
        return applicableRules;
    }
}
