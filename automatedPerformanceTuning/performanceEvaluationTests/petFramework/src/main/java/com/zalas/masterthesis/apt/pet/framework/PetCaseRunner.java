package com.zalas.masterthesis.apt.pet.framework;

import com.zalas.masterthesis.apt.pet.framework.petcaseprepare.PetCaseData;
import com.zalas.masterthesis.apt.pet.framework.petcaseprepare.PetCaseExtractor;
import com.zalas.masterthesis.apt.pet.framework.petcaseprepare.PetClassFinder;

import java.util.HashSet;
import java.util.Set;

public class PetCaseRunner {

    private Set<PerformanceIssue> performanceIssues = new HashSet<>();

    public void run(String pack4ge) {
        PetClassFinder petClassFinder = new PetClassFinder();
        Set<PetCaseData> petCaseData = new PetCaseExtractor(petClassFinder).extractPetCasesFromPackage(pack4ge);
        new PetCaseExecutor(petCaseData).execute();
    }


}
