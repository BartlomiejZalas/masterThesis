package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.cache;

import com.zalas.masterthesis.aptmodel.TrafficProfile;
import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClientException;
import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.zalas.masterthesis.aptmodel.TrafficProfile.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class EnableCacheRuleTest {

    @Mock
    private ConfigurationClient configurationClient;

    private EnableCacheRule enableCacheRule;

    @Before
    public void setUp() throws Exception {
        enableCacheRule = new EnableCacheRule(configurationClient);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenNotTrafficProfileMetricGiven() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("differentMetric", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, null);

        boolean ruleApplicable = enableCacheRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(ruleApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenTrafficProfileIsMutable() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("trafficProfile", MUTABLE.toString());
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, null);

        boolean ruleApplicable = enableCacheRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(ruleApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenCacheIsAlreadyEnabled() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("trafficProfile", IMMUTABLE.toString());
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, ConfigurationConstants.Value.CACHED, null);

        boolean ruleApplicable = enableCacheRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(ruleApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnTrue_whenTrafficProfileMetricAndTrafficProfileIsImmutableAndCacheIsAlreadyDisabled() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("trafficProfile", IMMUTABLE.toString());
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, ConfigurationConstants.Value.NO_CACHE, null);

        boolean ruleApplicable = enableCacheRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertTrue(ruleApplicable);
    }

    @Test
    public void executeAction() throws Exception {
        enableCacheRule.executeAction();
        then(configurationClient).should().setConfiguration(ConfigurationConstants.CACHE, ConfigurationConstants.Value.CACHED);
    }

}