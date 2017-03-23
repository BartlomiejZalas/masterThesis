package com.zalas.masterthesis.application.monitoring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodExecutionTime {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodExecutionTime.class);

    @Around("execution(* com.zalas.masterthesis.application.controller.rest.WebController.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.nanoTime();

        Object retVal = proceedingJoinPoint.proceed();

        long endTime = System.nanoTime();
        logExecutionTime(proceedingJoinPoint, startTime, endTime);
        return retVal;
    }

    private void logExecutionTime(ProceedingJoinPoint proceedingJoinPoint, long startTime, long endTime) {
        long duration = endTime - startTime;
        String methodName = proceedingJoinPoint.getSignature().getName();
        LOGGER.info("Execution time of " + methodName + "(...): [ns] " + duration);
    }

}
