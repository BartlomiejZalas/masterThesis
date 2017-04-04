package com.zalas.masterthesis.configurationserver.service;

import com.zalas.masterthesis.configurationserver.api.Configuration;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.zalas.masterthesis.configurationserver.api.Configuration.*;


@org.springframework.stereotype.Service
public class ConfigurationService implements InitializingBean {

    private Map<Configuration, Configuration.Value> configuration = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration.put(BATCH, Configuration.Value.DIRECT);
        configuration.put(CACHE, Configuration.Value.NO_CACHE);
        configuration.put(THREADS, Configuration.Value.T100);
    }

    public void change(String configurationKeyString, String newValueString) throws ConfigurationException {
        validateKeyString(configurationKeyString);
        validateValueString(newValueString);
        Configuration configurationKey = Configuration.valueOf(configurationKeyString);
        Configuration.Value value = Configuration.Value.valueOf(newValueString);
        validateIsAllowedValueForKey(configurationKey, value);

        configuration.put(configurationKey, value);
    }

    public String get(String configurationKeyString) throws ConfigurationException {
        validateKeyString(configurationKeyString);

        return configuration.get(Configuration.valueOf(configurationKeyString)).toString();
    }

    private void validateIsAllowedValueForKey(Configuration configurationKey, Configuration.Value value) throws ConfigurationException {
        if (!configurationKey.getPossibleValues().contains(value)) {
            throw new ConfigurationException("Configuration value " + value + " is not valid for: " + configurationKey + ". Use one of following:" + configurationKey.getPossibleValues());
        }
    }

    private void validateValueString(String newValueString) throws ConfigurationException {
        if (!Configuration.Value.contains(newValueString)) {
            throw new ConfigurationException("Configuration value " + newValueString + " is not one of following: " + Arrays.toString(Configuration.Value.values()));
        }
    }

    private void validateKeyString(String configurationKeyString) throws ConfigurationException {
        if (!Configuration.contains(configurationKeyString)) {
            throw new ConfigurationException("Configuration key " + configurationKeyString + " is not one of " + Arrays.toString(Configuration.values()));
        }
    }
}
