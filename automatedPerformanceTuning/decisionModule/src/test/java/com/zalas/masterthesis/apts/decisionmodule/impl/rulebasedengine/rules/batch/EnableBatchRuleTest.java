package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.batch;

import com.zalas.masterthesis.aptmodel.InsertsLevel;
import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.cache.EnableCacheRule;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class EnableBatchRuleTest {

    @Mock
    private ConfigurationClient configurationClient;

    private EnableBatchRule enableBatchRule;

    @Before
    public void setUp() throws Exception {
        enableBatchRule = new EnableBatchRule(configurationClient);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenDifferentMetricGiven() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("differentMetric", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, null);

        boolean isApplicable = enableBatchRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenInsertsLevelLow() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("insertsLevel", InsertsLevel.LOW.toString());
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, null);

        boolean isApplicable = enableBatchRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenBatchAlreadyEnabled() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("insertsLevel", InsertsLevel.HIGH.toString());
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(Value.BATCHED, null, null);

        boolean isApplicable = enableBatchRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnTrue_whenInsertsLevelMetricGivenAndInsertsLevelHisgAndBatchAlreadyDisabled() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("insertsLevel", InsertsLevel.HIGH.toString());
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(Value.DIRECT, null, null);

        boolean isApplicable = enableBatchRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertTrue(isApplicable);
    }

    @Test
    public void executeAction() throws Exception {
        enableBatchRule.executeAction();

        then(configurationClient).should().setConfiguration(BATCH, Value.BATCHED);
    }

}