package com.zalas.masterthesis.apts.decisionmodule.impl;

import com.zalas.masterthesis.apts.decisionmodule.api.PerformanceIssue;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.EmptyRule;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class RuleBasedEngineTest {

    private RuleBasedEngine ruleBasedEngine;

    @Before
    public void setUp() throws Exception {
        ruleBasedEngine = new RuleBasedEngine();
    }

    @Test
    public void getBestRule_shouldReturnBestRule_whenOnlyOneRuleIsApplicable() throws Exception {
        Rule rule1 = registerRule(false, 1);
        Rule rule2 = registerRule(true, 1);
        Rule rule3 = registerRule(false, 1);

        Rule bestRule = ruleBasedEngine.getBestRule(null, null);

        assertSame(rule2, bestRule);
    }

    @Test
    public void getBestRule_shouldReturnBestRuleBasedOnPriority_whenManyRulesAreApplicable() throws Exception {
        Rule rule1 = registerRule(true, 2);
        Rule rule2 = registerRule(true, 3);
        Rule rule3 = registerRule(true, 1);

        Rule bestRule = ruleBasedEngine.getBestRule(null, null);

        assertSame(rule2, bestRule);
    }

    @Test
    public void getBestRule_shouldReturnNoActionRule_whenNoRulesAreApplicable() throws Exception {
        registerRule(false, 1);
        registerRule(false, 1);
        registerRule(false, 1);

        Rule bestRule = ruleBasedEngine.getBestRule(null, null);

        assertEquals(EmptyRule.getInstance(), bestRule);
    }

    private Rule registerRule(boolean isApplicable, int priority) {
        Rule rule = new Rule() {
            @Override
            public boolean isRuleApplicable(PerformanceIssue performanceIssue, ApplicationConfiguration currentConfiguration) {
                return isApplicable;
            }

            @Override
            public void executeAction() {

            }

            @Override
            public int getPriority() {
                return priority;
            }
        };
        ruleBasedEngine.register(rule);
        return rule;
    }
}