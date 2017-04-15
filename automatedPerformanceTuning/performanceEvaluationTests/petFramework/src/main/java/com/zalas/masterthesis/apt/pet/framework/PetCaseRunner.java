package com.zalas.masterthesis.apt.pet.framework;

import com.zalas.masterthesis.apt.pet.framework.petcaseprepare.PetCaseData;
import com.zalas.masterthesis.apt.pet.framework.petcaseprepare.PetCaseExtractor;
import com.zalas.masterthesis.apt.pet.framework.petcaseprepare.PetClassFinder;
import com.zalas.masterthesis.apt.pet.framework.petcasescheduler.PetCaseExecutor;

import java.util.*;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class PetCaseRunner extends Thread {

    private String pack4ge;
    private Set<String> issues = Collections.synchronizedSet(new HashSet<String>());

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

        return new PetCaseExecutor(petCaseData, newScheduledThreadPool(petCaseData.size()), issues);
    }

    public Set<String> getIssues() {
        return issues;
    }
}
