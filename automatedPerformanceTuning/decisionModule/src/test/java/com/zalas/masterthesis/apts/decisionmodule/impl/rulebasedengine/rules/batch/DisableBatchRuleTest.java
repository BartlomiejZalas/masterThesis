package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.batch;

import com.zalas.masterthesis.aptmodel.InsertsLevel;
import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.BATCH;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class DisableBatchRuleTest {

    @Mock
    private ConfigurationClient configurationClient;

    private DisableBatchRule disableBatchRule;

    @Before
    public void setUp() throws Exception {
        disableBatchRule = new DisableBatchRule(configurationClient);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenDifferentMetricGiven() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("differentMetric", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, null);

        boolean isApplicable = disableBatchRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenInsertsLevelHigh() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("insertsLevel", InsertsLevel.HIGH.toString());
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, null);

        boolean isApplicable = disableBatchRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenBatchAlreadyDisabled() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("insertsLevel", InsertsLevel.LOW.toString());
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(Value.DIRECT, null, null);

        boolean isApplicable = disableBatchRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnTRue_whenInsertsLevelMetricGivenAndInsertsLevelLowAndBatchEnabled() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("insertsLevel", InsertsLevel.LOW.toString());
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(Value.BATCHED, null, null);

        boolean isApplicable = disableBatchRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertTrue(isApplicable);
    }

    @Test
    public void executeAction() throws Exception {
        disableBatchRule.executeAction();

        then(configurationClient).should().setConfiguration(BATCH, Value.DIRECT);
    }

}