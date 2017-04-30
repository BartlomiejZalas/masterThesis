package com.zalas.masterthesis.resourcemonitoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;

@org.springframework.stereotype.Service
public class MonitoringService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestService.class);

    private Future monitoringThread;

    public void start() throws MonitoringServiceUsageException {
        validateIsThreadInactive();
        clearOldData();
        monitoringThread = newSingleThreadScheduledExecutor().scheduleAtFixedRate(new MonitoringThread(), 0, 1, SECONDS);
        LOGGER.info("Monitoring started");
    }

    public void stop() throws MonitoringServiceUsageException {
        validateIsThreadActive();
        monitoringThread.cancel(true);
        LOGGER.info("Monitoring finished");
    }

    private void clearOldData() {
        new CpuUsageInfluxClient().clearMeasurement("resources");
    }

    private void validateIsThreadInactive() throws MonitoringServiceUsageException {
        if (monitoringThread != null && !monitoringThread.isDone()) {
            throw new MonitoringServiceUsageException("Monitoring active!");
        }
    }

    private void validateIsThreadActive() throws MonitoringServiceUsageException {
        if (monitoringThread == null || monitoringThread.isDone()) {
            throw new MonitoringServiceUsageException("Monitoring inactive!");
        }
    }
}
