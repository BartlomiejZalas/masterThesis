package com.zalas.masterthesis.apt.runner;

import com.zalas.masterthesis.apt.pet.framework.PetCaseRunner;

public class AptRunner {

    public static void main(String[] args) {
        new PetCaseRunner().run("com.zalas.masterthesis.apt.pet.cases");
    }
}
