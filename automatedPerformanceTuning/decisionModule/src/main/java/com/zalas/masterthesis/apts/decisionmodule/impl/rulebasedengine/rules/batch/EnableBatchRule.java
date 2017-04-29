package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.batch;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClientException;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

import static com.zalas.masterthesis.aptmodel.InsertsLevel.HIGH;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.BATCH;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value;

public class EnableBatchRule implements Rule {
    private ConfigurationClient configurationClient;

    public EnableBatchRule(ConfigurationClient configurationClient) {
        this.configurationClient = configurationClient;
    }

    @Override
    public boolean isRuleApplicable(IssueToHandle problem, ApplicationConfiguration configuration) {
        if (isInsertsLevel(problem) && isInsertsLevelHigh(problem) && isBatchDisabled(configuration)) {
            return true;
        }
        return false;
    }

    private boolean isInsertsLevel(IssueToHandle issueToHandle) {
        return issueToHandle.getMetric().equals("insertsLevel");
    }

    private boolean isInsertsLevelHigh(IssueToHandle issueToHandle) {
        return issueToHandle.getReason().equals(HIGH.toString());
    }

    private boolean isBatchDisabled(ApplicationConfiguration currentConfiguration) {
        return currentConfiguration.getBatch().equals(Value.DIRECT);
    }

    @Override
    public void executeAction() {
        try {
            configurationClient.setConfiguration(BATCH, Value.BATCHED);
            System.out.println("action: enable batch");
        } catch (ConfigurationClientException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
