package com.zalas.masterthesis.apts.pet.framework.assertions;

import org.junit.Assert;
import org.junit.Test;

import static com.zalas.masterthesis.apts.pet.framework.assertions.PetAssert.assertKpiEqual;
import static com.zalas.masterthesis.apts.pet.framework.assertions.PetAssert.assertKpiGreaterThan;
import static com.zalas.masterthesis.apts.pet.framework.assertions.PetAssert.assertKpiLessThan;
import static org.junit.Assert.*;

public class PetAssertTest {
    @Test
    public void assertKpiLessThan_shouldThrowException_whenExceeded() throws Exception {
        try {
            assertKpiLessThan("KPI", 12., 10.);
            fail();
        } catch (PerformanceIssue e) {
            assertEquals("KPI", e.getMetric());
            Assert.assertEquals(Reason.EXCEDEED.toString(), e.getReason());
        }
    }

    @Test
    public void assertKpiLessThan_shouldPass_whenNotExceeded() throws Exception {
        assertKpiLessThan("KPI", 1., 2.);
    }

    @Test
    public void  assertKpiGreaterThan_shouldThrowException_whenUnder() throws Exception {
        try {
            assertKpiGreaterThan("KPI", 8., 10.);
            fail();
        } catch (PerformanceIssue e) {
            assertEquals("KPI", e.getMetric());
            Assert.assertEquals(Reason.UNDER.toString(), e.getReason());
        }
    }

    @Test
    public void  assertKpiGreaterThan_shouldPass_whenNotUnder() throws Exception {
        assertKpiGreaterThan("KPI", 2., 1.);
    }

    @Test
    public void assertKpiEqual_shouldThrowException_whenNotEqualAndGreater() throws Exception {
        try {
            assertKpiEqual("KPI", 12., 10.);
            fail();
        } catch (PerformanceIssue e) {
            assertEquals("KPI", e.getMetric());
            Assert.assertEquals(Reason.NOT_EQUAL.toString(), e.getReason());
        }
    }

    @Test
    public void assertKpiEqual_shouldThrowException_whenNotEqualAndLess() throws Exception {
        try {
            assertKpiEqual("KPI", 8., 10.);
            fail();
        } catch (PerformanceIssue e) {
            assertEquals("KPI", e.getMetric());
            Assert.assertEquals(Reason.NOT_EQUAL.toString(), e.getReason());
        }
    }

    @Test
    public void assertKpiEqual_shouldPass_whenEqual() throws Exception {
        assertKpiEqual("KPI", 1., 1.);
    }
}