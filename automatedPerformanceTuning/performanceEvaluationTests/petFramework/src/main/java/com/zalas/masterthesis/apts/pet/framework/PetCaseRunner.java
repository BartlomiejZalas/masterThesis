package com.zalas.masterthesis.apts.pet.framework;

import com.zalas.masterthesis.apts.pet.framework.petcaseprepare.PetCaseData;
import com.zalas.masterthesis.apts.pet.framework.petcaseprepare.PetCaseExtractor;
import com.zalas.masterthesis.apts.pet.framework.petcaseprepare.PetClassFinder;
import com.zalas.masterthesis.apts.pet.framework.petcasescheduler.PetCaseExecutor;

import java.util.Set;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class PetCaseRunner extends Thread {

    private String pack4ge;
    private PerformanceIssues performanceIssues = new PerformanceIssues();

    public PetCaseRunner(String pack4ge) {
        this.pack4ge = pack4ge;
    }

    @Override
    public void run() {
        createExecutor().execute();
    }

    private PetCaseExecutor createExecutor() {
        PetClassFinder petClassFinder = new PetClassFinder(this.pack4ge);
        PetCaseExtractor petCaseExtractor = new PetCaseExtractor(petClassFinder);
        Set<PetCaseData> petCaseData = petCaseExtractor.extractPetCasesFromPackage();

        return new PetCaseExecutor(petCaseData, newScheduledThreadPool(petCaseData.size()), performanceIssues);
    }

    public Set<PerformanceIssue> getNewIssues() {
        Set<PerformanceIssue> performanceIssues = this.performanceIssues.getPerformanceIssues();
        this.performanceIssues.clear();
        return performanceIssues;
    }
}
