package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.cache;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.zalas.masterthesis.aptmodel.TrafficProfile.IMMUTABLE;
import static com.zalas.masterthesis.aptmodel.TrafficProfile.MUTABLE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class DisableCacheRuleTest {

    @Mock
    private ConfigurationClient configurationClient;

    private DisableCacheRule disableCacheRule;

    @Before
    public void setUp() throws Exception {
        disableCacheRule = new DisableCacheRule(configurationClient);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenNotTrafficProfileMetricGiven() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("differentMetric", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, null);

        boolean ruleApplicable = disableCacheRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(ruleApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenTrafficProfileIsImmutable() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("trafficProfile", IMMUTABLE.toString());
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, null);

        boolean ruleApplicable = disableCacheRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(ruleApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenCacheIsAlreadyDisabled() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("trafficProfile", MUTABLE.toString());
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, ConfigurationConstants.Value.NO_CACHE, null);

        boolean ruleApplicable = disableCacheRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(ruleApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnTrue_whenTrafficProfileMetricAndTrafficProfileIsImmutableAndCacheIsAlreadyDisabled() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("trafficProfile", MUTABLE.toString());
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, ConfigurationConstants.Value.CACHED, null);

        boolean ruleApplicable = disableCacheRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertTrue(ruleApplicable);
    }

    @Test
    public void executeAction() throws Exception {
        disableCacheRule.executeAction();
        then(configurationClient).should().setConfiguration(ConfigurationConstants.CACHE, ConfigurationConstants.Value.NO_CACHE);
    }

}