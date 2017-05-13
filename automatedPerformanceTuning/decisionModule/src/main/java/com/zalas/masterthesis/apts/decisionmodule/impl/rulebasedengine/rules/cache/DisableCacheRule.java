package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.cache;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.api.Rule;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClientException;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;

import static com.zalas.masterthesis.aptmodel.TrafficProfile.MUTABLE;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.CACHE;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value;

public class DisableCacheRule implements Rule {
    private ConfigurationClient configurationClient;

    public DisableCacheRule(ConfigurationClient configurationClient) {
        this.configurationClient = configurationClient;
    }

    @Override
    public boolean isRuleApplicable(IssueToHandle problem, ApplicationConfiguration configuration) {
        if (isTrafficProfile(problem) && isTrafficMutable(problem) && isCacheEnabled(configuration)) {
            return true;
        }
        return false;
    }

    private boolean isTrafficProfile(IssueToHandle issueToHandle) {
        return issueToHandle.getMetric().equals("trafficProfile");
    }

    private boolean isTrafficMutable(IssueToHandle issueToHandle) {
        return issueToHandle.getStatus().equals(MUTABLE.toString());
    }

    private boolean isCacheEnabled(ApplicationConfiguration currentConfiguration) {
        return currentConfiguration.getCache().equals(Value.CACHED);
    }

    @Override
    public void executeAction() {
//        try {
//            configurationClient.setConfiguration(CACHE, Value.NO_CACHE);
//            System.out.println("action: disable cache");
//        } catch (ConfigurationClientException e) {
//            System.out.println(e.getMessage());
//        }
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
