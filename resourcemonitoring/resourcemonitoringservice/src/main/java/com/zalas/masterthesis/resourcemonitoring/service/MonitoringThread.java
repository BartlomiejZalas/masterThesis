package com.zalas.masterthesis.resourcemonitoring.service;


import com.sun.management.OperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;

public class MonitoringThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestService.class);

    private CpuUsageInfluxClient cpuUsageInfluxClient = new CpuUsageInfluxClient();

    @Override
    public void run() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        double systemCpuLoad = osBean.getSystemCpuLoad();
        cpuUsageInfluxClient.saveCpuUsage(systemCpuLoad);
        LOGGER.info("CPU: " + systemCpuLoad);
    }
}
