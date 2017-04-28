package com.zalas.masterthesis.apts.pet.framework;

public class PerformanceIssueTO {

    private final String metric;
    private final String reason;

    public PerformanceIssueTO(String metric, String reason) {
        this.metric = metric;
        this.reason = reason;
    }

    public String getMetric() {
        return metric;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "PerformanceIssueTO{" +
                "metric='" + metric + '\'' +
                ", reason=" + reason +
                '}';
    }
}
