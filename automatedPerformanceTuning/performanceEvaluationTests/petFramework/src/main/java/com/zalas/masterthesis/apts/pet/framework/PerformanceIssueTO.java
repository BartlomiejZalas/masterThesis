package com.zalas.masterthesis.apts.pet.framework;

import com.zalas.masterthesis.apts.pet.framework.assertions.Reason;

public class PerformanceIssueTO {

    private final String kpiName;
    private final String reason;

    public PerformanceIssueTO(String metric, String reason) {
        this.kpiName = metric;
        this.reason = reason;
    }

    public String getKpiName() {
        return kpiName;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "PerformanceIssueTO{" +
                "kpiName='" + kpiName + '\'' +
                ", reason=" + reason +
                '}';
    }
}
