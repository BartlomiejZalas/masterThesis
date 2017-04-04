package com.zalas.masterthesis.configurationserver.service;

import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationConstantsServiceTest {

    private ConfigurationService configurationService;

    @Before
    public void setUp() throws Exception {
        configurationService = new ConfigurationService();
    }

    @Test
    public void afterPropertiesSet_shouldCreateDefaultConfiguration() throws Exception {
        configurationService.afterPropertiesSet();

        assertEquals(ConfigurationConstants.Value.DIRECT.toString(), configurationService.get(ConfigurationConstants.BATCH.toString()));
        assertEquals(ConfigurationConstants.Value.NO_CACHE.toString(), configurationService.get(ConfigurationConstants.CACHE.toString()));
        assertEquals(ConfigurationConstants.Value.T100.toString(), configurationService.get(ConfigurationConstants.THREADS.toString()));
    }

    @Test(expected = ConfigurationException.class)
    public void change_shouldThrowException_whenBadKeyGiven() throws Exception {
        configurationService.change("BAD_KEY", "BATCHED");
    }

    @Test(expected = ConfigurationException.class)
    public void change_shouldThrowException_whenBadValueAdded() throws Exception {
        configurationService.change("BATCH", "BAD_VALUE");
    }

    @Test(expected = ConfigurationException.class)
    public void change_shouldThrowException_whenValueMismatchWithKeyOccured() throws Exception {
        configurationService.change("BATCH", "CACHED");
    }

    @Test
    public void change_shouldUpdateConfiguration_whenProperParametersGiven() throws Exception {
        configurationService.change("BATCH", "BATCHED");

        assertEquals("BATCHED", configurationService.get("BATCH"));
    }

}