package com.zalas.masterthesis.apts.decisionmodule.api;

public class IssueToHandle {
    private final String metric;
    private final String reason;

    public IssueToHandle(String metric, String reason) {
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
