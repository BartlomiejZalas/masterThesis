package com.zalas.masterthesis.configurationserver.service;

import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.*;

@org.springframework.stereotype.Service
public class ConfigurationService implements InitializingBean {

    private Map<ConfigurationConstants, ConfigurationConstants.Value> configuration = new HashMap<>();

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationService.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration.put(BATCH, ConfigurationConstants.Value.DIRECT);
        configuration.put(CACHE, ConfigurationConstants.Value.NO_CACHE);
        configuration.put(THREADS, ConfigurationConstants.Value.T50);
        LOG.info("Default configuration created: " + configuration);
    }

    public void change(String configurationKeyString, String newValueString) throws ConfigurationException {
        validateKeyString(configurationKeyString);
        validateValueString(newValueString);
        ConfigurationConstants configurationConstantKey = ConfigurationConstants.valueOf(configurationKeyString);
        ConfigurationConstants.Value value = ConfigurationConstants.Value.valueOf(newValueString);
        validateIsAllowedValueForKey(configurationConstantKey, value);

        configuration.put(configurationConstantKey, value);
    }

    public String get(String configurationKeyString) throws ConfigurationException {
        validateKeyString(configurationKeyString);

        return configuration.get(ConfigurationConstants.valueOf(configurationKeyString)).toString();
    }

    public ApplicationConfiguration getConfiguration() {
        return new ApplicationConfiguration(configuration.get(BATCH), configuration.get(CACHE), configuration.get(THREADS));
    }

    private void validateIsAllowedValueForKey(ConfigurationConstants configurationConstantKey, ConfigurationConstants.Value value) throws ConfigurationException {
        if (!configurationConstantKey.getPossibleValues().contains(value)) {
            throw new ConfigurationException("ConfigurationConstants value " + value + " is not valid for: " + configurationConstantKey + ". Use one of following:" + configurationConstantKey.getPossibleValues());
        }
    }

    private void validateValueString(String newValueString) throws ConfigurationException {
        if (!ConfigurationConstants.Value.contains(newValueString)) {
            throw new ConfigurationException("ConfigurationConstants value " + newValueString + " is not one of following: " + Arrays.toString(ConfigurationConstants.Value.values()));
        }
    }

    private void validateKeyString(String configurationKeyString) throws ConfigurationException {
        if (!ConfigurationConstants.contains(configurationKeyString)) {
            throw new ConfigurationException("ConfigurationConstants key " + configurationKeyString + " is not one of " + Arrays.toString(ConfigurationConstants.values()));
        }
    }
}
