package com.zalas.masterthesis.apts.pet.cases;

import com.zalas.masterthesis.apts.pet.framework.annotations.PetCase;

import static com.zalas.masterthesis.apts.pet.framework.assertions.PetAssert.assertKpiLessThan;
import static org.junit.Assert.assertThat;

//@Pet
public class DummyCase {

    @PetCase(monitorIntervalInSec = 2, durationInSec = 10)
    public void dummyTestCase() throws Exception{
        assertKpiLessThan("executionTime", 1., 2.);
    }

    @PetCase(monitorIntervalInSec = 1, durationInSec = 5)
    public void secondTestCase() {
        assertKpiLessThan("executionTime", 1., 2.);
    }
}
