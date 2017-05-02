package com.zalas.masterthesis.apts.decisionmodule.main;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.apts.decisionmodule.impl.RuleBasedEngine;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClientException;
import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.*;

public class DecisionModule {
    private RuleBasedEngine aptsRuleBasedEngine;

    public DecisionModule(RuleBasedEngine aptsRuleBasedEngine) {
        this.aptsRuleBasedEngine = aptsRuleBasedEngine;
    }

    public void performDecision(IssueToHandle issueToHandle) throws ConfigurationClientException {
        ConfigurationClient configurationClient = new ConfigurationClient();
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(
                configurationClient.getConfiguration(BATCH),
                configurationClient.getConfiguration(CACHE),
                configurationClient.getConfiguration(THREADS)
        );

        Rule bestRule = aptsRuleBasedEngine.getBestRule(issueToHandle, applicationConfiguration);
        bestRule.executeAction();
    }
}
