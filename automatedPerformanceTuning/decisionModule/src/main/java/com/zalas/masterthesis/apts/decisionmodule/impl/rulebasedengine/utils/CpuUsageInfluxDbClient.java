package com.zalas.masterthesis.apts.decisionmodule.impl.rulebasedengine.utils;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.List;

import static java.lang.String.format;

public class CpuUsageInfluxDbClient {
    private final InfluxDB connection;
    private static final String DB_NAME = "pet";
    private static final String COUNT_TP_TEMP = "select mean(cpu) from resources WHERE time > now() - %ds";

    public CpuUsageInfluxDbClient() {
        connection = InfluxDBFactory.connect("http://192.168.56.20:8086");
    }

    public double getMeanCpuUsage(int monitorIntervalInSec) {
        Query query = new Query(format(COUNT_TP_TEMP, monitorIntervalInSec), DB_NAME);
        QueryResult queryResult = connection.query(query);
        List<QueryResult.Series> series = queryResult.getResults().get(0).getSeries();
        if (series == null) {
            return 0;
        }
        return (Double) series.get(0).getValues().get(0).get(1);
    }
}
