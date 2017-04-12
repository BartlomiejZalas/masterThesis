package com.zalas.masterthesis.apt.pet.framework.petcasescheduler;

import com.zalas.masterthesis.apt.pet.framework.petcaseprepare.PetCaseData;

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

    public PetCaseExecutor(Set<PetCaseData> petCaseData, ScheduledExecutorService scheduledExecutorService) {
        this.petCaseData = petCaseData;
        this.scheduledExecutor = scheduledExecutorService;
    }

    public void execute() {
        List<ScheduledFuture> scheduledWorkers = newArrayList();

        for (PetCaseData petCase : petCaseData) {
            ScheduledFuture petCaseWorkerHandler = schedulePetCase(petCase, new PetCaseInvokeWorker(petCase));
            schedulePetCaseTerminator(petCase, petCaseWorkerHandler);
            scheduledWorkers.add(petCaseWorkerHandler);
        }

        waitUntilAllTasksAreFinished(scheduledWorkers);
        scheduledExecutor.shutdown();
    }

    private void schedulePetCaseTerminator(PetCaseData petCase, ScheduledFuture handler) {
        scheduledExecutor.schedule(new PetCaseTerminatorWorker(handler), petCase.getDurationInSec(), TimeUnit.SECONDS);
    }

    private ScheduledFuture<?> schedulePetCase(PetCaseData petCase, PetCaseInvokeWorker petCaseInvokeWorker) {
        return scheduledExecutor.scheduleAtFixedRate(petCaseInvokeWorker, 0, petCase.getMonitorIntervalInSec(), TimeUnit.SECONDS);
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
}