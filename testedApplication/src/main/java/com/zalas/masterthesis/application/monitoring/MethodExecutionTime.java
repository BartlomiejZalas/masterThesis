package com.zalas.masterthesis.application.monitoring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Aspect
@Component
public class MethodExecutionTime {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodExecutionTime.class);
    private InfluxClient influxClient = new InfluxClient();


    @Around("execution(* com.zalas.masterthesis.application.controller.rest.WebController.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.nanoTime();

        Object retVal = proceedingJoinPoint.proceed();

        long duration = System.nanoTime() - startTime;
        String methodName = proceedingJoinPoint.getSignature().getName();

        logExecutionTime(methodName, duration);
        saveExecutionTime(methodName, duration);

        return retVal;
    }

    private void saveExecutionTime(String methodName, long duration) {
        influxClient.saveExecutionTime(duration, methodName, "?");
    }

    private void logExecutionTime(String methodName, long duration) {
        LOGGER.info("Execution time of " + methodName + "(...): [ns] " + duration);
    }

}
