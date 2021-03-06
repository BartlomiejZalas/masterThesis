package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.rules.threads;

import com.zalas.masterthesis.apts.decisionmodule.api.IssueToHandle;
import com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.utils.CpuUsageInfluxDbClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.THREADS;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class SwitchTo50ThreadsRuleTest {

    @Mock
    private ConfigurationClient configurationClient;
    @Mock
    private CpuUsageInfluxDbClient cpuUsageInfluxDbClient;

    private SwitchTo50ThreadsRule switchTo50ThreadsRule;

    @Before
    public void setUp() throws Exception {
        switchTo50ThreadsRule = new SwitchTo50ThreadsRule(configurationClient, cpuUsageInfluxDbClient);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenDifferentMetricIsGiven() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("differentMetric", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, null);

        boolean isApplicable = switchTo50ThreadsRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenThreadNumberIs50() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("executionTime", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, Value.T50);

        boolean isApplicable = switchTo50ThreadsRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenThreadNumberIs20AndCpuUsageHigh() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("executionTime", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, Value.T20);
        given(cpuUsageInfluxDbClient.getMeanCpuUsage(5)).willReturn(0.9);

        boolean isApplicable = switchTo50ThreadsRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnTrue_whenThreadNumberIs20AndCpuUsageLow() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("executionTime", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, Value.T20);
        given(cpuUsageInfluxDbClient.getMeanCpuUsage(5)).willReturn(0.5);

        boolean isApplicable = switchTo50ThreadsRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertTrue(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenThreadNumberIs80AndCpuUsageLow() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("executionTime", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, Value.T80);
        given(cpuUsageInfluxDbClient.getMeanCpuUsage(5)).willReturn(0.5);

        boolean isApplicable = switchTo50ThreadsRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertFalse(isApplicable);
    }

    @Test
    public void isRuleApplicable_shouldReturnFalse_whenThreadNumberIs80AndCpuUsageHigh() throws Exception {
        IssueToHandle issueToHandle = new IssueToHandle("executionTime", null);
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(null, null, Value.T80);
        given(cpuUsageInfluxDbClient.getMeanCpuUsage(5)).willReturn(0.9);

        boolean isApplicable = switchTo50ThreadsRule.isRuleApplicable(issueToHandle, applicationConfiguration);

        assertTrue(isApplicable);
    }

    @Test
    public void executeAction() throws Exception {
        switchTo50ThreadsRule.executeAction();

        then(configurationClient).should().setConfiguration(THREADS, Value.T50);
    }

}