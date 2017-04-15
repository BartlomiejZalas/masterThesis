package com.zalas.masterthesis.apt.pet.cases;

import com.zalas.masterthesis.apt.pet.framework.annotations.Pet;
import com.zalas.masterthesis.apt.pet.framework.annotations.PetCase;

import static com.zalas.masterthesis.apt.pet.framework.assertions.PetAssert.assertKpiLessThan;

@Pet
public class MeanExecutionTimePET {

    @PetCase
    public void meanExecutionTime_shouldBeOnAcceptableLevel() throws Exception {
        double meanExecutionTimeFromLastOneMinute = 1200.5641;

        assertKpiLessThan("executionTime", meanExecutionTimeFromLastOneMinute, 1200.0);
    }

}
