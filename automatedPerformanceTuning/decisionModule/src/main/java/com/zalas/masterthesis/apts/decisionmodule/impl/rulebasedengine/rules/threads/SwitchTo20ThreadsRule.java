package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.threads;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClientException;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.BATCH;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.THREADS;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value;

public class SwitchTo20ThreadsRule implements Rule {
    private ConfigurationClient configurationClient;

    public SwitchTo20ThreadsRule(ConfigurationClient configurationClient) {
        this.configurationClient = configurationClient;
    }

    @Override
    public boolean isRuleApplicable(IssueToHandle problem, ApplicationConfiguration configuration) {
        if (isExecutionTimeExceeded(problem) && isThreadNumberDifferent(configuration) && cpuUsageIsHigh()) {
            return true;
        }
        return false;
    }

    private boolean isExecutionTimeExceeded(IssueToHandle issueToHandle) {
        return issueToHandle.getMetric().equals("executionTime");
    }

    private boolean isThreadNumberDifferent(ApplicationConfiguration currentConfiguration) {
        return currentConfiguration.getThreads().equals(Value.T20);
    }

    private boolean cpuUsageIsHigh() {
        return false;
    }

    @Override
    public void executeAction() {
        try {
            configurationClient.setConfiguration(THREADS, Value.T20);
            System.out.println("action: threads switched to 20");
        } catch (ConfigurationClientException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
