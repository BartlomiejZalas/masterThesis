package com.zalas.masterthesis.application.service.simulation;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CpuExhaustorTest {
    @Test
    @Ignore
    public void executeCpuExhaustingTask_shouldTookGivenNumberOfSeconds() throws Exception {
        int seconds = 1;
        CpuExhaustor cpuExhaustor = new CpuExhaustor();

        DateTime start = new DateTime();
        cpuExhaustor.executeCpuExhaustingTask(seconds);
        DateTime end = new DateTime();

        int diffInSeconds = Seconds.secondsBetween(start, end).getSeconds();
        assertTrue(diffInSeconds >= seconds);
        assertTrue(diffInSeconds < seconds + 1);
    }

}