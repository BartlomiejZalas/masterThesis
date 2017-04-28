package com.zalas.masterthesis.apts.decisionmodule.api;

import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

public interface Rule {

    boolean isRuleApplicable(ProblemToSolve problemToSolve, ApplicationConfiguration currentConfiguration);

    void executeAction();

    int getPriority();
}
