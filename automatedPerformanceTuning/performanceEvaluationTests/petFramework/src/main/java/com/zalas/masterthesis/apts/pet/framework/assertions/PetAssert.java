package com.zalas.masterthesis.apts.pet.framework.assertions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PetAssert {

    public static void assertKpiLessThan(String kpiName, Double currentValue, Double limit) {
        try {
            assertThat(currentValue, lessThan(limit));
        } catch (AssertionError e) {
            throw new KpiAssertionException(e.getMessage(), kpiName, Reason.EXCEDEED,
                    calculatePercentageDeviation(currentValue, limit));
        }
    }

    public static void assertKpiGreaterThan(String kpiName, Double currentValue, Double limit) {
        try {
            assertThat(currentValue, greaterThan(limit));
        } catch (AssertionError e) {
            throw new KpiAssertionException(e.getMessage(), kpiName, Reason.UNDER,
                    calculatePercentageDeviation(currentValue, limit));
        }
    }

    public static void assertKpiEqual(String kpiName, Double currentValue, Double expectedValue) {
        try {
            assertThat(currentValue, equalTo(expectedValue));
        } catch (AssertionError e) {
            throw new KpiAssertionException(e.getMessage(), kpiName, Reason.NOT_EQUAL, calculatePercentageDeviation(currentValue, expectedValue));
        }
    }

    private static double calculatePercentageDeviation(Double currentValue, Double expectedValue) {
        return Math.abs((currentValue-expectedValue) / expectedValue * 100);
    }
}
