package com.zalas.masterthesis.apts.pet.cases;

import com.zalas.masterthesis.aptmodel.TrafficProfile;
import com.zalas.masterthesis.apts.pet.framework.annotations.Pet;
import com.zalas.masterthesis.apts.pet.framework.annotations.PetCase;
import com.zalas.masterthesis.apts.pet.framework.assertions.PerformanceIssue;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.List;
import java.util.Map;

import static com.zalas.masterthesis.aptmodel.TrafficProfile.IMMUTABLE;
import static com.zalas.masterthesis.aptmodel.TrafficProfile.MUTABLE;

@Pet
public class MonitorTrafficProfilePET {

    private static final int MONITOR_INTERVAL = 10;

    @PetCase(durationInSec = 60, monitorIntervalInSec = MONITOR_INTERVAL)
    public void monitorMutabilityOfTraffic_shouldReportIssueWhenChanged() {
        System.out.println("checking profile");
        TrafficProfile trafficProfile = getTrafficProfile(MONITOR_INTERVAL);
        System.out.println("profile is " + trafficProfile);

        throw new PerformanceIssue("trafficProfile monitoring", "trafficProfile", trafficProfile.toString());
    }

    private TrafficProfile getTrafficProfile(int monitorInterval) {
        InfluxDB influxDB = InfluxDBFactory.connect("http://192.168.56.20:8086");
        Query query = new Query("select count(*) from execution_time WHERE time > now() - " + monitorInterval + "s  group by *", "pet");
        QueryResult queryResult = influxDB.query(query);

        List<QueryResult.Series> series = queryResult.getResults().get(0).getSeries();

        double countImmutable = getCountForTag(series, IMMUTABLE.toString());
        double countMutable = getCountForTag(series, MUTABLE.toString());

        return countImmutable > countMutable ? IMMUTABLE : MUTABLE;
    }

    private double getCountForTag(List<QueryResult.Series> series, String tag) {
        if (series == null) return 0;
        for (QueryResult.Series serie : series) {
            Map<String, String> tags = serie.getTags();
            if (tags.containsValue(tag)) {
                return (Double) serie.getValues().get(0).get(1);
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        new MonitorTrafficProfilePET().monitorMutabilityOfTraffic_shouldReportIssueWhenChanged();
    }


}
