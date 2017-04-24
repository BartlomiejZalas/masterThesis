package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules;

import com.zalas.masterthesis.apts.decisionmodule.api.PerformanceIssue;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;

public class DummyRule implements Rule {
    @Override
    public boolean isRuleApplicable(PerformanceIssue performanceIssue) {
        return true;
    }

    @Override
    public void executeAction() {
        System.out.println("Execute action " + getClass().getSimpleName());
    }
}
