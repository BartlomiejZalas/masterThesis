package com.zalas.masterthesis.apts.pet.cases;

import com.zalas.masterthesis.apts.pet.framework.annotations.Pet;
import com.zalas.masterthesis.apts.pet.framework.annotations.PetCase;

import static com.zalas.masterthesis.apts.pet.framework.assertions.PetAssert.assertKpiLessThan;

@Pet
public class MeanExecutionTimePET {

    @PetCase(enabled = false, durationInSec = 60, monitorIntervalInSec = 20)
    public void meanExecutionTime_shouldBeOnAcceptableLevel() throws Exception {
        double meanExecutionTimeFromLastOneMinute = 1200.5641;

        assertKpiLessThan("executionTime", meanExecutionTimeFromLastOneMinute, 1200.0);
    }

}
