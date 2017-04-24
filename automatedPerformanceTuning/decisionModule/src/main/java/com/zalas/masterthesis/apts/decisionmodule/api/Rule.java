package com.zalas.masterthesis.apts.decisionmodule.api;

public interface Rule {

    boolean isRuleApplicable(PerformanceIssue performanceIssue);

    void executeAction();
}
