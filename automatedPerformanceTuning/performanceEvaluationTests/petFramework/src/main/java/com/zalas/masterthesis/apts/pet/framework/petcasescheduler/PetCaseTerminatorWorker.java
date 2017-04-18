package com.zalas.masterthesis.apts.pet.framework.petcasescheduler;

import java.util.concurrent.ScheduledFuture;

public class PetCaseTerminatorWorker implements Runnable {

    private ScheduledFuture handler;

    public PetCaseTerminatorWorker(ScheduledFuture handler) {
        this.handler = handler;
    }

    public void run() {
        handler.cancel(true);
    }
}
