package com.zalas.masterthesis.apts.decisionmodule.main;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.apts.decisionmodule.impl.RuleBasedEngine;
import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

public class DecisionModule {
    private RuleBasedEngine aptsRuleBasedEngine;

    public DecisionModule(RuleBasedEngine aptsRuleBasedEngine) {
        this.aptsRuleBasedEngine = aptsRuleBasedEngine;
    }

    public void performDecision(IssueToHandle issueToHandle) {
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(
                ConfigurationConstants.Value.BATCHED,
                ConfigurationConstants.Value.NO_CACHE,
                ConfigurationConstants.Value.T20
        );
        Rule bestRule = aptsRuleBasedEngine.getBestRule(issueToHandle, applicationConfiguration);
        bestRule.executeAction();
    }
}
