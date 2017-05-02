package com.zalas.masterthesis.apts.pet.cases;

import com.zalas.masterthesis.apts.pet.framework.annotations.Pet;
import com.zalas.masterthesis.apts.pet.framework.annotations.PetCase;
import com.zalas.masterthesis.apts.pet.utils.ExecutionTimeInfluxDbClient;

import static com.zalas.masterthesis.apts.pet.framework.assertions.PetAssert.assertKpiLessThan;

@Pet
public class MeanExecutionTimePET {

    private static final int MONITORING_INTERVAL = 20;
    private ExecutionTimeInfluxDbClient executionTimeInfluxDbClient = new ExecutionTimeInfluxDbClient();


    @PetCase(enabled = false, durationInSec = 60, monitorIntervalInSec = MONITORING_INTERVAL)
    public void meanExecutionTime_shouldBeOnAcceptableLevel() throws Exception {
        double meanExecutionTime = getMeanExecutionTimeInMillis(MONITORING_INTERVAL);

        System.out.println(meanExecutionTime);
        assertKpiLessThan("executionTime", meanExecutionTime, 1200.0);
    }

    private double getMeanExecutionTimeInMillis(int monitoringInterval) {
        return executionTimeInfluxDbClient.getMeanExecutionTime(3600)/1000000;
    }

}
