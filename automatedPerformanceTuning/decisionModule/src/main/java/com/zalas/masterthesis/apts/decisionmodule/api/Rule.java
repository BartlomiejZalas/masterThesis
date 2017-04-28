package com.zalas.masterthesis.apts.decisionmodule.api;

import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

public interface Rule {

    boolean isRuleApplicable(IssueToHandle issueToHandle, ApplicationConfiguration currentConfiguration);

    void executeAction();

    int getPriority();
}
