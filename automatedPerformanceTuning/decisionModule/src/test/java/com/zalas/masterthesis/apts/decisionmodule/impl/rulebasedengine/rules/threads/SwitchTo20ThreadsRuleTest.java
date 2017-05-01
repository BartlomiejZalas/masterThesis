package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.threads;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.utils.CpuUsageInfluxDbClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class SwitchTo20ThreadsRuleTest {

    @Mock
    private ConfigurationClient configurationClient;
    @Mock
    private CpuUsageInfluxDbClient cpuUsageInfluxDbClient;

    private SwitchTo20ThreadsRule switchTo20ThreadsRule;

    @Before
    public void setUp() throws Exception {
        switchTo20ThreadsRule = new SwitchTo20ThreadsRule(configurationClient, cpuUsageInfluxDbClient);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenDifferentMetricIsGiven() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("differentMetric", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, null);

        boolean isApplicable = switchTo20ThreadsRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenThreadNumberIs20() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("executionTime", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, ConfigurationConstants.Value.T20);

        boolean isApplicable = switchTo20ThreadsRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenCpuUsageIsLow() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("executionTime", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, ConfigurationConstants.Value.T50);
        given(cpuUsageInfluxDbClient.getMeanCpuUsage(5)).willReturn(0.5);

        boolean isApplicable = switchTo20ThreadsRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnTrue_whenMeticIsExecutionTimeAndCurrentThreadsNumberIsDifferentAndCpuUsageIsHish() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("executionTime", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, ConfigurationConstants.Value.T50);
        given(cpuUsageInfluxDbClient.getMeanCpuUsage(5)).willReturn(0.9);

        boolean isApplicable = switchTo20ThreadsRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertTrue(isApplicable);
    }

    @Test
    public void executeAction() throws Exception {
        switchTo20ThreadsRule.executeAction();

        then(configurationClient).should().setConfiguration(ConfigurationConstants.THREADS, ConfigurationConstants.Value.T20);
    }

}