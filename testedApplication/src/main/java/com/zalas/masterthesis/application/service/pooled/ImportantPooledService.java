package com.zalas.masterthesis.application.service.pooled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class ImportantPooledService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportantPooledService.class);

    private int myId = (int)(Math.random() *1000);

    public void doImportantThing() {
        LOGGER.info("ImportantPooledService id: " + myId);
        simulateSlowService();
    }

    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
