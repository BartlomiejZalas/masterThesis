package com.zalas.masterthesis.apt.pet.framework.assertions;

public class KpiAssertionException extends RuntimeException {

    private final String kpiName;
    private final Reason reason;
    private final double percentageDeviation;

    public KpiAssertionException(String message, String kpiName, Reason reason, double percentageDeviation) {
        super(message);
        this.kpiName = kpiName;
        this.reason = reason;
        this.percentageDeviation = percentageDeviation;
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
}
