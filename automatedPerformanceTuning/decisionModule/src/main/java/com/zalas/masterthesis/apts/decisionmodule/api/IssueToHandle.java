package com.zalas.masterthesis.apts.decisionmodule.api;

public class IssueToHandle {
    private final String metric;
    private final String status;

    public IssueToHandle(String metric, String status) {
        this.metric = metric;
        this.status = status;
    }

    public String getMetric() {
        return metric;
    }

    public String getStatus() {
        return status;
    }
}
