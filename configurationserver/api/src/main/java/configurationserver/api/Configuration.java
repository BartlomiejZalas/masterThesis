package configurationserver.api;

import java.util.HashSet;
import java.util.Set;

import static configurationserver.api.Configuration.Value.*;
import static java.util.Arrays.asList;

public enum Configuration {

    BATCH(BATCHED, DIRECT),
    CACHE(CACHED, NO_CACHE),
    THREADS(T100, T200, T300);

    private final Set<Value> possibleValues;

    Configuration(Value... possibleValues) {
        this.possibleValues = new HashSet(asList(possibleValues));
    }

    public Set<Value> getPossibleValues() {
        return possibleValues;
    }

    public enum Value {
        BATCHED, DIRECT, CACHED, NO_CACHE, T100, T200, T300;

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
        for (Configuration c : Configuration.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}
