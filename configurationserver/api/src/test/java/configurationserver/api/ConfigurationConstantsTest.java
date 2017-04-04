package configurationserver.api;

import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import org.junit.Test;

import static com.google.common.collect.Sets.newHashSet;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.BATCH;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value.BATCHED;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value.DIRECT;
import static org.junit.Assert.*;

public class ConfigurationConstantsTest {

    @Test
    public void configurationEnum_shouldProvideComfortableApi() throws Exception {
        assertEquals("BATCH", BATCH.toString());
        assertEquals("BATCHED", BATCHED.toString());
        assertEquals("DIRECT", DIRECT.toString());

        assertEquals(newHashSet(BATCHED, DIRECT), BATCH.getPossibleValues());

        assertTrue(ConfigurationConstants.contains("BATCH"));
        assertFalse(ConfigurationConstants.contains("BAD_ENUM"));

        assertTrue(ConfigurationConstants.Value.contains("BATCHED"));
        assertFalse(ConfigurationConstants.Value.contains("BAD_ENUM"));
    }
}