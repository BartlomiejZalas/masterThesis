package com.zalas.masterthesis.configurationserver.service;

import configurationserver.api.Configuration;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static configurationserver.api.Configuration.*;

@org.springframework.stereotype.Service
public class ConfigurationService implements InitializingBean {

    private Map<Configuration, Configuration.Value> configuration = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration.put(BATCH, Value.DIRECT);
        configuration.put(CACHE, Value.NO_CACHE);
        configuration.put(THREADS, Value.T100);
    }

    public void change(String configurationKeyString, String newValueString) throws ConfigurationException {
        validateKeyString(configurationKeyString);
        validateValueString(newValueString);
        Configuration configurationKey = valueOf(configurationKeyString);
        Value value = Value.valueOf(newValueString);
        validateIsAllowedValueForKey(configurationKey, value);

        configuration.put(configurationKey, value);
    }

    public String get(String configurationKeyString) throws ConfigurationException {
        validateKeyString(configurationKeyString);

        return configuration.get(valueOf(configurationKeyString)).toString();
    }

    private void validateIsAllowedValueForKey(Configuration configurationKey, Value value) throws ConfigurationException {
        if (!configurationKey.getPossibleValues().contains(value)) {
            throw new ConfigurationException("Configuration value " + value + " is not valid for: " + configurationKey + ". Use one of following:" + configurationKey.getPossibleValues());
        }
    }

    private void validateValueString(String newValueString) throws ConfigurationException {
        if (!Value.contains(newValueString)) {
            throw new ConfigurationException("Configuration value " + newValueString + " is not one of following: " + Arrays.toString(Value.values()));
        }
    }

    private void validateKeyString(String configurationKeyString) throws ConfigurationException {
        if (!contains(configurationKeyString)) {
            throw new ConfigurationException("Configuration key " + configurationKeyString + " is not one of " +  Arrays.toString(values()));
        }
    }
}
