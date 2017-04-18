package com.zalas.masterthesis.apts.pet.framework;

import com.google.common.base.Objects;
import com.zalas.masterthesis.apts.pet.framework.assertions.Reason;

public class PerformanceIssue {

    private final String testName;
    private final String kpiName;
    private final Reason reason;
    private final double percentageDeviation;
    private final String message;

    public PerformanceIssue(String testName, String kpiName, Reason reason, double percentageDeviation, String message) {
        this.testName = testName;
        this.kpiName = kpiName;
        this.reason = reason;
        this.percentageDeviation = percentageDeviation;
        this.message = message;
    }

    public String getTestName() {
        return testName;
    }

    public String getKpiName() {
        return kpiName;
    }

    public Reason getReason() {
        return reason;
    }

    public double getPercentageDeviation() {
        return percentageDeviation;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerformanceIssue that = (PerformanceIssue) o;
        return Objects.equal(testName, that.testName) &&
                Objects.equal(kpiName, that.kpiName) &&
                reason == that.reason;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(testName, kpiName, reason);
    }

    @Override
    public String toString() {
        return "PerformanceIssue{" +
                "testName='" + testName + '\'' +
                ", kpiName='" + kpiName + '\'' +
                ", reason=" + reason +
                ", percentageDeviation=" + percentageDeviation +
                '}';
    }
}
