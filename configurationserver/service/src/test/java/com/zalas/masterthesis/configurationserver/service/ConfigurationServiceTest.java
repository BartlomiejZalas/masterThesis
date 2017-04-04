package com.zalas.masterthesis.configurationserver.service;

import com.zalas.masterthesis.configurationserver.api.Configuration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationServiceTest {

    private ConfigurationService configurationService;

    @Before
    public void setUp() throws Exception {
        configurationService = new ConfigurationService();
    }

    @Test
    public void afterPropertiesSet_shouldCreateDefaultConfiguration() throws Exception {
        configurationService.afterPropertiesSet();

        assertEquals(Configuration.Value.DIRECT.toString(), configurationService.get(Configuration.BATCH.toString()));
        assertEquals(Configuration.Value.NO_CACHE.toString(), configurationService.get(Configuration.CACHE.toString()));
        assertEquals(Configuration.Value.T100.toString(), configurationService.get(Configuration.THREADS.toString()));
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