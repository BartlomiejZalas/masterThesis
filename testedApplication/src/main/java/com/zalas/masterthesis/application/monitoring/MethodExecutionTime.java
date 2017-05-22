package com.zalas.masterthesis.application.monitoring;

import com.zalas.masterthesis.aptmodel.TrafficProfile;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static com.zalas.masterthesis.aptmodel.TrafficProfile.*;

@Aspect
@Component
public class MethodExecutionTime {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodExecutionTime.class);
    private ExecutionTimeInfluxClient executionTimeInfluxClient = new ExecutionTimeInfluxClient();


    @Around("execution(* com.zalas.masterthesis.application.controller.rest.WebController.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.nanoTime();

        Object retVal = proceedingJoinPoint.proceed();

        long duration = System.nanoTime() - startTime;
        Method method = getMethod(proceedingJoinPoint);
        logExecutionTime(method.getName(), duration, getTrafficProfile(method));
        saveExecutionTime(method.getName(), duration, getTrafficProfile(method));

        return retVal;
    }

    private Method getMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        return methodSignature.getMethod();
    }

    private TrafficProfile getTrafficProfile(Method method) {
        if (method.getName().equals("workaround")) {
            return MUTABLE;
        }

        Annotation annotation = method.getAnnotations()[0];
        String annotationName = annotation.annotationType().getSimpleName();
        return mapMethodToProfile(annotationName);
    }

    private TrafficProfile mapMethodToProfile(String annotationName) {
        return annotationName.equals("GetMapping")? IMMUTABLE : MUTABLE;
    }

    private void saveExecutionTime(String methodName, long duration, TrafficProfile trafficProfile) {
        try {
            executionTimeInfluxClient.saveExecutionTime(duration, methodName, trafficProfile.toString());
        } catch (Exception e) {
            LOGGER.error("Cannot save execution time in influx: "+ e.getMessage(), e);
        }
    }

    private void logExecutionTime(String methodName, long duration, TrafficProfile trafficProfile) {
        LOGGER.info("Execution time of " + methodName + "(...): [ns] " + duration + ", trafficProfile: "+trafficProfile);
    }

}
