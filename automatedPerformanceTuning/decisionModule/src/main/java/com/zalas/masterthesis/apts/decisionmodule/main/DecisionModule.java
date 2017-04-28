package com.zalas.masterthesis.apts.decisionmodule.main;

import com.zalas.masterthesis.apts.decisionmodule.api.ProblemToSolve;
import com.zalas.masterthesis.apts.decisionmodule.impl.RuleBasedEngine;
import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

public class DecisionModule {
    private RuleBasedEngine aptsRuleBasedEngine;

    public DecisionModule(RuleBasedEngine aptsRuleBasedEngine) {

        this.aptsRuleBasedEngine = aptsRuleBasedEngine;
    }

    public void performDecision(ProblemToSolve problemToSolve) {
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(ConfigurationConstants.Value.BATCHED, ConfigurationConstants.Value.CACHED, ConfigurationConstants.Value.T100);



        aptsRuleBasedEngine.getBestRule(problemToSolve, applicationConfiguration);
    }
}
