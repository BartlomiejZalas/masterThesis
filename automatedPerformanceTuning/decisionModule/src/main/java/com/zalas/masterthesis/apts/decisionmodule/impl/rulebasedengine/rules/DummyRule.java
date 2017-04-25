package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules;

import com.zalas.masterthesis.apts.decisionmodule.api.PerformanceIssue;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

public class DummyRule implements Rule {
    @Override
    public boolean isRuleApplicable(PerformanceIssue performanceIssue, ApplicationConfiguration currentConfiguration) {
        return true;
    }

    @Override
    public void executeAction() {
        System.out.println("Execute action " + getClass().getSimpleName());
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
