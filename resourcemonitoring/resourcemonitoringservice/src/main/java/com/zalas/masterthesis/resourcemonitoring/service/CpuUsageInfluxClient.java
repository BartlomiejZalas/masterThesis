package com.zalas.masterthesis.resourcemonitoring.service;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

import java.util.concurrent.TimeUnit;

public class CpuUsageInfluxClient {

    private final InfluxDB connection;
    private static final String DB_NAME = "pet";

    public CpuUsageInfluxClient() {
        connection = InfluxDBFactory.connect("http://192.168.56.20:8086");
    }

    public void saveCpuUsage(double cpuUsage) {

        Point point = Point.measurement("resources")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("cpu", cpuUsage)
                .build();

        BatchPoints batchPoints = createBatch();
        batchPoints.point(point);
        connection.write(batchPoints);
    }

    public void clearMeasurement(String measurement) {
        connection.query(new Query("drop measurement " + measurement, DB_NAME));
    }

    private BatchPoints createBatch() {
        return BatchPoints
                    .database(DB_NAME)
                    .tag("async", "true")
                    .retentionPolicy("autogen")
                    .consistency(InfluxDB.ConsistencyLevel.ALL)
                    .build();
    }

}
