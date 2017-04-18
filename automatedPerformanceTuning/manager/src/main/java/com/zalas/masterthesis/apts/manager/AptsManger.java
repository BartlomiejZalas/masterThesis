package com.zalas.masterthesis.apts.manager;

import com.zalas.masterthesis.apts.pet.framework.PetCaseRunner;

public class AptsManger {

    public static void main(String[] args) throws InterruptedException {

        PetCaseRunner runner = new PetCaseRunner("com.zalas.masterthesis.apts.pet.cases");
        runner.start();

        while(runner.isAlive()) {
            Thread.sleep(1000);
            System.out.println(runner.getNewIssues());
        }
    }
}
