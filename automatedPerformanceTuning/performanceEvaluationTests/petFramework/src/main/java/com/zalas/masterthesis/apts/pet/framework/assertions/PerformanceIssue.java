package com.zalas.masterthesis.apts.pet.framework.assertions;

public class PerformanceIssue extends RuntimeException {

    private final String metric;
    private final String reason;

    public PerformanceIssue(String message, String metric, String reason) {
        super(message);
        this.metric = metric;
        this.reason = reason;
    }

    public String getMetric() {
        return metric;
    }

    public String getReason() {
        return reason;
    }
}
