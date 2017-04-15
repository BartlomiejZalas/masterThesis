package com.zalas.masterthesis.apt.runner;

import com.zalas.masterthesis.apt.pet.framework.PetCaseRunner;

public class AptRunner {

    public static void main(String[] args) throws InterruptedException {

        PetCaseRunner runner = new PetCaseRunner("com.zalas.masterthesis.apt.pet.cases");
        runner.start();

        while(runner.isAlive()) {
            Thread.sleep(1000);
            System.out.println(runner.getNewIssues());
        }
    }
}
