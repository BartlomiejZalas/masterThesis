package com.zalas.masterthesis.apts.pet.framework.assertions;

import static com.zalas.masterthesis.apts.pet.framework.assertions.Reason.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PetAssert {

    public static void assertKpiLessThan(String kpiName, Double currentValue, Double limit) {
        try {
            assertThat(currentValue, lessThan(limit));
        } catch (AssertionError e) {
            throw new PerformanceIssue(e.getMessage(), kpiName, EXCEDEED.toString());
        }
    }

    public static void assertKpiGreaterThan(String kpiName, Double currentValue, Double limit) {
        try {
            assertThat(currentValue, greaterThan(limit));
        } catch (AssertionError e) {
            throw new PerformanceIssue(e.getMessage(), kpiName, UNDER.toString());
        }
    }

    public static void assertKpiEqual(String kpiName, Double currentValue, Double expectedValue) {
        try {
            assertThat(currentValue, equalTo(expectedValue));
        } catch (AssertionError e) {
            throw new PerformanceIssue(e.getMessage(), kpiName, NOT_EQUAL.toString());
        }
    }

    private static double calculatePercentageDeviation(Double currentValue, Double expectedValue) {
        return Math.abs((currentValue-expectedValue) / expectedValue * 100);
    }

    public static void assertKpiEqual(String kpiName, String expected, String current) {
        try {
            assertThat(expected, equalTo(current));
        } catch (AssertionError e) {
            throw new PerformanceIssue(e.getMessage(), kpiName, NOT_EQUAL.toString());
        }
    }
}
