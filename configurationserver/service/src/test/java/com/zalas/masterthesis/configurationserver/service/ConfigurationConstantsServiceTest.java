package com.zalas.masterthesis.configurationserver.service;

import org.junit.Before;
import org.junit.Test;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.*;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value.*;
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

        assertEquals(DIRECT.toString(), configurationService.get(BATCH.toString()));
        assertEquals(NO_CACHE.toString(), configurationService.get(CACHE.toString()));
        assertEquals(T50.toString(), configurationService.get(THREADS.toString()));
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