package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.threads;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.utils.CpuUsageInfluxDbClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClientException;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.THREADS;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value;

public class SwitchTo80ThreadsRule implements Rule {
    private ConfigurationClient configurationClient;
    private CpuUsageInfluxDbClient cpuUsageInfluxDbClient;

    public SwitchTo80ThreadsRule(ConfigurationClient configurationClient, CpuUsageInfluxDbClient cpuUsageInfluxDbClient) {
        this.configurationClient = configurationClient;
        this.cpuUsageInfluxDbClient = cpuUsageInfluxDbClient;
    }

    @Override
    public boolean isRuleApplicable(IssueToHandle problem, ApplicationConfiguration configuration) {
        if (isExecutionTimeExceeded(problem) && isThreadNumberDifferent(configuration) && cpuUsageIsLow()) {
            return true;
        }
        return false;
    }

    private boolean isExecutionTimeExceeded(IssueToHandle issueToHandle) {
        return issueToHandle.getMetric().equals("executionTime");
    }

    private boolean isThreadNumberDifferent(ApplicationConfiguration currentConfiguration) {
        return !currentConfiguration.getThreads().equals(Value.T80);
    }

    private boolean cpuUsageIsLow() {
        return cpuUsageInfluxDbClient.getMeanCpuUsage(5) <= 0.8;
    }

    @Override
    public void executeAction() {
        try {
            configurationClient.setConfiguration(THREADS, Value.T80);
            System.out.println("action: threads switched to 80");
        } catch (ConfigurationClientException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
