package com.zalas.masterthesis.apts.pet.cases;

import com.zalas.masterthesis.aptmodel.TrafficProfile;
import com.zalas.masterthesis.apts.pet.framework.annotations.Pet;
import com.zalas.masterthesis.apts.pet.framework.annotations.PetCase;
import com.zalas.masterthesis.apts.pet.framework.assertions.PerformanceIssue;
import com.zalas.masterthesis.apts.pet.utils.ExecutionTimeInfluxDbClient;

import static com.zalas.masterthesis.aptmodel.TrafficProfile.IMMUTABLE;
import static com.zalas.masterthesis.aptmodel.TrafficProfile.MUTABLE;

@Pet
public class MonitorTrafficProfilePET {

    private static final int MONITOR_INTERVAL = 10;
    private ExecutionTimeInfluxDbClient executionTimeInfluxDbClient = new ExecutionTimeInfluxDbClient();

    @PetCase(enabled = true, durationInSec = 1500, monitorIntervalInSec = MONITOR_INTERVAL, delayInSec = MONITOR_INTERVAL)
    public void monitorMutabilityOfTraffic_shouldReportIssueWhenChanged() {
        TrafficProfile trafficProfile = getTrafficProfile(MONITOR_INTERVAL);

        throw new PerformanceIssue("trafficProfile monitoring", "trafficProfile", trafficProfile.toString());
    }

    private TrafficProfile getTrafficProfile(int monitorInterval) {
        int countMutable = executionTimeInfluxDbClient.getCountForTrafficProfile(MUTABLE, monitorInterval);
        System.out.println(countMutable);
        return countMutable == 0 ? IMMUTABLE : MUTABLE;
    }
}
