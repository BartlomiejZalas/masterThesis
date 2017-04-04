package configurationserver.api;

import com.zalas.masterthesis.configurationserver.api.Configuration;
import org.junit.Test;

import static com.google.common.collect.Sets.newHashSet;
import static com.zalas.masterthesis.configurationserver.api.Configuration.BATCH;
import static com.zalas.masterthesis.configurationserver.api.Configuration.Value.BATCHED;
import static com.zalas.masterthesis.configurationserver.api.Configuration.Value.DIRECT;
import static org.junit.Assert.*;

public class ConfigurationTest {

    @Test
    public void configurationEnum_shouldProvideComfortableApi() throws Exception {
        assertEquals("BATCH", BATCH.toString());
        assertEquals("BATCHED", BATCHED.toString());
        assertEquals("DIRECT", DIRECT.toString());

        assertEquals(newHashSet(BATCHED, DIRECT), BATCH.getPossibleValues());

        assertTrue(Configuration.contains("BATCH"));
        assertFalse(Configuration.contains("BAD_ENUM"));

        assertTrue(Configuration.Value.contains("BATCHED"));
        assertFalse(Configuration.Value.contains("BAD_ENUM"));
    }
}