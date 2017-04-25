package com.zalas.masterthesis.apts.decisionmodule.api;

import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

public interface Rule {

    boolean isRuleApplicable(PerformanceIssue performanceIssue, ApplicationConfiguration currentConfiguration);

    void executeAction();

    int getPriority();
}
