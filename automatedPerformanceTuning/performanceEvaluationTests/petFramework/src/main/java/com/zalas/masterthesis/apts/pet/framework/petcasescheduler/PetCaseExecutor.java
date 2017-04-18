package com.zalas.masterthesis.apts.pet.framework.petcasescheduler;

import com.zalas.masterthesis.apts.pet.framework.PerformanceIssues;
import com.zalas.masterthesis.apts.pet.framework.petcaseprepare.PetCaseData;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Lists.newArrayList;

public class PetCaseExecutor {
    private final Set<PetCaseData> petCaseData;
    private ScheduledExecutorService scheduledExecutor;
    private PerformanceIssues issues;

    public PetCaseExecutor(Set<PetCaseData> petCaseData, ScheduledExecutorService scheduledExecutorService, PerformanceIssues issues) {
        this.petCaseData = petCaseData;
        this.scheduledExecutor = scheduledExecutorService;
        this.issues = issues;
    }

    public void execute() {
        List<ScheduledFuture> scheduledWorkers = newArrayList();

        for (PetCaseData petCase : petCaseData) {
            ScheduledFuture petCaseWorkerHandler = schedulePetCase(petCase.getMonitorIntervalInSec(), new PetCaseInvokeWorker(petCase, issues));
            schedulePetCaseTerminator(petCase.getDurationInSec(), petCaseWorkerHandler);
            scheduledWorkers.add(petCaseWorkerHandler);
        }

        waitUntilAllTasksAreFinished(scheduledWorkers);
        scheduledExecutor.shutdown();
    }

    private void schedulePetCaseTerminator(int durationInSec, ScheduledFuture handler) {
        scheduledExecutor.schedule(new PetCaseTerminatorWorker(handler), durationInSec, TimeUnit.SECONDS);
    }

    private ScheduledFuture<?> schedulePetCase(int intervalInSec, PetCaseInvokeWorker petCaseInvokeWorker) {
        return scheduledExecutor.scheduleAtFixedRate(petCaseInvokeWorker, 0, intervalInSec, TimeUnit.SECONDS);
    }

    private void waitUntilAllTasksAreFinished(List<ScheduledFuture> scheduledWorkers) {
        while (true) {
            if (scheduledWorkers.stream().allMatch(Future::isDone)) {
                break;
            }
            waitOneSecond();
        }
    }

    private void waitOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public PerformanceIssues getIssues() {
        return issues;
    }
}
