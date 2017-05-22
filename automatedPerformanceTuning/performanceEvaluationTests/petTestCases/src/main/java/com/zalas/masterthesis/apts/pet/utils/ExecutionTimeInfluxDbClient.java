package com.zalas.masterthesis.apts.pet.utils;

import com.zalas.masterthesis.aptmodel.TrafficProfile;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.List;

import static java.lang.String.format;

public class ExecutionTimeInfluxDbClient {
    private final InfluxDB connection;
    private static final String DB_NAME = "pet";
    private static final String COUNT_TP_TEMP =
            "select count(*) from execution_time WHERE time > now() - %ds and trafficProfile='%s'";
    private static final String COUNT_INSERTS =
            "select count(*) from execution_time WHERE time > now() - %ds and (methodName='add' or methodName='workaround')";
    private static final String MEAN_ET = "select mean(*) from execution_time WHERE time > now() - %ds";


    public ExecutionTimeInfluxDbClient() {
        connection = InfluxDBFactory.connect("http://192.168.56.20:8086");
    }

    public int getCountForTrafficProfile(TrafficProfile trafficProfile, int monitorInterval) {
        Query query = new Query(format(COUNT_TP_TEMP, monitorInterval, trafficProfile.toString()), DB_NAME);
        return getResultColumn(query).intValue();
    }

    public int getInserts(int monitorInterval) {
        Query query = new Query(format(COUNT_INSERTS, monitorInterval), DB_NAME);
        return getResultColumn(query).intValue();
    }

    private Double getResultColumn(Query query) {
        QueryResult queryResult = connection.query(query);
        List<QueryResult.Series> series = queryResult.getResults().get(0).getSeries();
        if (series == null) {
            return 0.;
        }
        return (Double) series.get(0).getValues().get(0).get(1);

    }

    public double getMeanExecutionTime(int monitoringInterval) {
        Query query = new Query(format(MEAN_ET, monitoringInterval), DB_NAME);
        return getResultColumn(query);
    }
}
