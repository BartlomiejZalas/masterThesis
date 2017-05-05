package com.zalas.masterthesis.apts.pet.cases;

import com.zalas.masterthesis.aptmodel.InsertsLevel;
import com.zalas.masterthesis.apts.pet.framework.annotations.Pet;
import com.zalas.masterthesis.apts.pet.framework.annotations.PetCase;
import com.zalas.masterthesis.apts.pet.framework.assertions.PerformanceIssue;
import com.zalas.masterthesis.apts.pet.utils.ExecutionTimeInfluxDbClient;

import static com.zalas.masterthesis.aptmodel.InsertsLevel.HIGH;
import static com.zalas.masterthesis.aptmodel.InsertsLevel.LOW;

@Pet
public class MonitorInsertsLevelPET {

    private static final int MONITOR_INTERVAL = 10;
    private ExecutionTimeInfluxDbClient executionTimeInfluxDbClient = new ExecutionTimeInfluxDbClient();

    @PetCase(enabled = false, durationInSec = 240, monitorIntervalInSec = MONITOR_INTERVAL)
    public void monitorMutabilityOfTraffic_shouldReportIssueWhenChanged() {
        InsertsLevel insertsLevel = getInsertsLevel(MONITOR_INTERVAL);

        throw new PerformanceIssue("insertsLevel monitoring", "insertsLevel", insertsLevel.toString());
    }

    private InsertsLevel getInsertsLevel(int monitorInterval) {
        int inserts = executionTimeInfluxDbClient.getInserts(monitorInterval);
        double insertsPerSeconds = (double) inserts / (double) monitorInterval;
        return (insertsPerSeconds < 10) ? LOW : HIGH;
    }
}
