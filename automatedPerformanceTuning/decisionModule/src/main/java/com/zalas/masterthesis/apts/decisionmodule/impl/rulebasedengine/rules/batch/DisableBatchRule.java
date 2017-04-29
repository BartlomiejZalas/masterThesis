package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.batch;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClientException;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

import static com.zalas.masterthesis.aptmodel.InsertsLevel.LOW;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.BATCH;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value;

public class DisableBatchRule implements Rule {
    private ConfigurationClient configurationClient;

    public DisableBatchRule(ConfigurationClient configurationClient) {
        this.configurationClient = configurationClient;
    }

    @Override
    public boolean isRuleApplicable(IssueToHandle problem, ApplicationConfiguration configuration) {
        if (isInsertsLevel(problem) && isInsertsLevelLow(problem) && isBatchEnabled(configuration)) {
            return true;
        }
        return false;
    }

    private boolean isInsertsLevel(IssueToHandle issueToHandle) {
        return issueToHandle.getMetric().equals("insertsLevel");
    }

    private boolean isInsertsLevelLow(IssueToHandle issueToHandle) {
        return issueToHandle.getStatus().equals(LOW.toString());
    }

    private boolean isBatchEnabled(ApplicationConfiguration currentConfiguration) {
        return currentConfiguration.getBatch().equals(Value.BATCHED);
    }

    @Override
    public void executeAction() {
        try {
            configurationClient.setConfiguration(BATCH, Value.DIRECT);
            System.out.println("action: disable batch");
        } catch (ConfigurationClientException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
