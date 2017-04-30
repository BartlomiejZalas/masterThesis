package com.zalas.masterthesis.configurationserver.api.constants;

import java.util.HashSet;
import java.util.Set;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value.*;
import static java.util.Arrays.asList;

public enum ConfigurationConstants {

    BATCH(BATCHED, DIRECT),
    CACHE(CACHED, NO_CACHE),
    THREADS(T20, T50, T80);

    private final Set<Value> possibleValues;

    ConfigurationConstants(Value... possibleValues) {
        this.possibleValues = new HashSet(asList(possibleValues));
    }

    public Set<Value> getPossibleValues() {
        return possibleValues;
    }

    public enum Value {
        BATCHED, DIRECT, CACHED, NO_CACHE, T20, T50, T80;

        public static boolean contains(String test) {
            for (Value c : Value.values()) {
                if (c.name().equals(test)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean contains(String test) {
        for (ConfigurationConstants c : ConfigurationConstants.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}
