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
    private MeasurementsInfluxDbClient measurementsInfluxDbClient = new MeasurementsInfluxDbClient();

    @PetCase(durationInSec = 60, monitorIntervalInSec = MONITOR_INTERVAL)
    public void monitorMutabilityOfTraffic_shouldReportIssueWhenChanged() {
        TrafficProfile trafficProfile = getTrafficProfile(MONITOR_INTERVAL);

        throw new PerformanceIssue("trafficProfile monitoring", "trafficProfile", trafficProfile.toString());
    }

    private TrafficProfile getTrafficProfile(int monitorInterval) {
        int countImmutable = measurementsInfluxDbClient.getCountForTrafficProfile(IMMUTABLE, monitorInterval);
        int countMutable = measurementsInfluxDbClient.getCountForTrafficProfile(MUTABLE, monitorInterval);
        return countImmutable >= countMutable ? IMMUTABLE : MUTABLE;
    }
}
