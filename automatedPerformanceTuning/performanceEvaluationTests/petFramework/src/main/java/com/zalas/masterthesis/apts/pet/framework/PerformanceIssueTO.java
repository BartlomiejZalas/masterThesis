package com.zalas.masterthesis.apts.pet.framework;

public class PerformanceIssueTO {

    private final String metric;
    private final String status;

    public PerformanceIssueTO(String metric, String status) {
        this.metric = metric;
        this.status = status;
    }

    public String getMetric() {
        return metric;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "PerformanceIssueTO{" +
                "metric='" + metric + '\'' +
                ", status=" + status +
                '}';
    }
}
