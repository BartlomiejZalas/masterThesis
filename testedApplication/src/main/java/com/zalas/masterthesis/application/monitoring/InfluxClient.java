package com.zalas.masterthesis.application.monitoring;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

public class InfluxClient {

    private final InfluxDB connection;
    private static final String DB_NAME = "pet";

    public InfluxClient() {
        connection = InfluxDBFactory.connect("http://192.168.56.20:8086");
    }

    public void saveExecutionTime(long executionTime, String methodName, String trafficProfile) {

        Point point = Point.measurement("execution_time")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("duration", executionTime)
                .addField("methodName", methodName)
                .tag("trafficProfile", trafficProfile)
                .build();

        BatchPoints batchPoints = createBatch();
        batchPoints.point(point);
        connection.write(batchPoints);
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
