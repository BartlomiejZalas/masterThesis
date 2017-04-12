package com.zalas.masterthesis.apt.pet.framework;

import com.zalas.masterthesis.apt.pet.framework.petcaseprepare.PetCaseData;
import com.zalas.masterthesis.apt.pet.framework.petcaseprepare.PetCaseExtractor;
import com.zalas.masterthesis.apt.pet.framework.petcaseprepare.PetClassFinder;
import com.zalas.masterthesis.apt.pet.framework.petcasescheduler.PetCaseExecutor;

import java.util.Set;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class PetCaseRunner {

    public void run(String pack4ge) {
        createExecutor(pack4ge).execute();
    }

    private PetCaseExecutor createExecutor(String pack4ge) {
        PetClassFinder petClassFinder = new PetClassFinder(pack4ge);
        PetCaseExtractor petCaseExtractor = new PetCaseExtractor(petClassFinder);
        Set<PetCaseData> petCaseData = petCaseExtractor.extractPetCasesFromPackage();

        return new PetCaseExecutor(petCaseData, newScheduledThreadPool(petCaseData.size()));
    }
}
