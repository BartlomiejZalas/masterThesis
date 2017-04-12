package com.zalas.masterthesis.apt.pet.cases;

import com.zalas.masterthesis.apt.pet.framework.annotations.Pet;
import com.zalas.masterthesis.apt.pet.framework.annotations.PetCase;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@Pet
public class DummyCase {

    @PetCase(monitorIntervalInSec = 2, durationInSec = 10)
    public void dummyTestCase() throws Exception{
        assertThat("dummyTest", 1, greaterThan(2));
    }

    @PetCase(monitorIntervalInSec = 1, durationInSec = 5)
    public void secondTestCase() {
        assertThat("dummyTest2", 1, greaterThan(2));
    }
}
