package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.cache;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClientException;
import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

import static com.zalas.masterthesis.aptmodel.TrafficProfile.IMMUTABLE;

public class EnableCacheRule implements Rule {
    private ConfigurationClient configurationClient;

    public EnableCacheRule(ConfigurationClient configurationClient) {
        this.configurationClient = configurationClient;
    }

    @Override
    public boolean isRuleApplicable(IssueToHandle problem, ApplicationConfiguration configuration) {
        if (isTrafficProfile(problem) && isTrafficImmutable(problem) && isCacheDisabled(configuration)) {
            return true;
        }
        return false;
    }

    private boolean isTrafficProfile(IssueToHandle issueToHandle) {
        return issueToHandle.getMetric().equals("trafficProfile");
    }

    private boolean isTrafficImmutable(IssueToHandle issueToHandle) {
        return issueToHandle.getStatus().equals(IMMUTABLE.toString());
    }

    private boolean isCacheDisabled(ApplicationConfiguration currentConfiguration) {
        return currentConfiguration.getCache().equals(ConfigurationConstants.Value.NO_CACHE);
    }

    @Override
    public void executeAction() {
        try {
            configurationClient.setConfiguration(ConfigurationConstants.CACHE, ConfigurationConstants.Value.CACHED);
            System.out.println("action: enable cache");
        } catch (ConfigurationClientException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
