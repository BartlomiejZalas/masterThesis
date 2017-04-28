package com.zalas.masterthesis.apts.decisionmodule.impl;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertSame;

public class RuleComparatorFromHighToLowTest {

    @Test
    public void compare_shouldSortRulesFromHighPriorityToLow() throws Exception {
        Rule rule1 = createRuleWithPriority(2);
        Rule rule2 = createRuleWithPriority(3);
        Rule rule3 = createRuleWithPriority(1);

        List<Rule> rules = asList(rule1, rule2, rule3);
        System.out.println(rules);
        rules.sort(new RuleComparatorFromHighToLow());

        System.out.println(rules);

        assertSame(rule2, rules.get(0));
        assertSame(rule1, rules.get(1));
        assertSame(rule3, rules.get(2));
    }

    private Rule createRuleWithPriority(int priority) {
        return new Rule() {
            @Override
            public boolean isRuleApplicable(IssueToHandle issueToHandle, ApplicationConfiguration currentConfiguration) {
                return false;
            }

            @Override
            public void executeAction() {

            }

            @Override
            public int getPriority() {
                return priority;
            }
        };
    }

}