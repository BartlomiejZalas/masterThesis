package com.zalas.masterthesis.apt.pet.cases;

import com.zalas.masterthesis.apt.pet.framework.annotations.Pet;
import com.zalas.masterthesis.apt.pet.framework.annotations.PetCase;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

@Pet
public class MeanExecutionTimePET {

    @PetCase
    public void meanExecutionTime_shouldBeOnAcceptableLevel() throws Exception{
        double meanExecutionTimeFromLastOneMinute = 1200.5641;

        assertThat(meanExecutionTimeFromLastOneMinute, lessThan(1200.0));
    }

}
