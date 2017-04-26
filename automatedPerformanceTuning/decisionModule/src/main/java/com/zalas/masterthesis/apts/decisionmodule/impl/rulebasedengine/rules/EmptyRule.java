package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules;

import com.zalas.masterthesis.apts.decisionmodule.api.PerformanceIssue;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

public class EmptyRule {

    private static Rule instance = createDoNothingRule();

    private EmptyRule() {
    }

    public static Rule getInstance() {
        return instance;
    }

    private static Rule createDoNothingRule() {
        return new Rule() {
            @Override
            public boolean isRuleApplicable(PerformanceIssue performanceIssue, ApplicationConfiguration currentConfiguration) {
                return true;
            }

            @Override
            public void executeAction() {

            }

            @Override
            public int getPriority() {
                return 0;
            }
        };
    }
}
