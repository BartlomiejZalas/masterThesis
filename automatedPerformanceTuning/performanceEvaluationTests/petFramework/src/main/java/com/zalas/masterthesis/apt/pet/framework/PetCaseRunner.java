package com.zalas.masterthesis.apt.pet.framework;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

public class PetCaseRunner {

    private static final String PET_CLASS_SUFFIX = "PET";

    private Set<PerformanceIssue> performanceIssues = new HashSet<>();

    public void run() {
        Set<String> petClassesNames = getPetClassesNames();
        Set<PetCaseInvokeData> petMethodsInvokations = getPetMethodsInvokations(petClassesNames);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(petMethodsInvokations.size());
        List<ScheduledFuture> scheduledWorkers = newArrayList();

        for (PetCaseInvokeData petCase : petMethodsInvokations) {
            PetCaseWorker petCaseWorker = new PetCaseWorker(petCase);
            final ScheduledFuture handler = executor.scheduleAtFixedRate(petCaseWorker, 0, petCase.getTestCaseAnnotation().monitorIntervalInSec(), TimeUnit.SECONDS);
            executor.schedule(new Runnable() {
                public void run() {
                    System.out.println("cancelling" + handler);
                    handler.cancel(true);
                }
            }, petCase.getTestCaseAnnotation().durationInSec(), TimeUnit.SECONDS);
            scheduledWorkers.add(handler);
        }

        while (true) {
            boolean allFinished = scheduledWorkers.stream().allMatch(Future::isDone);
            System.out.println("allFinished?: " + allFinished);
            if (allFinished) {
                executor.shutdown();
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println("We've done here");
    }

    private Set<PetCaseInvokeData> getPetMethodsInvokations(Set<String> petClassesNames) {
        Set<PetCaseInvokeData> petCaseInvokeData = new HashSet<>();
        for (String className : petClassesNames) {
            Class clazz = getClassForName(className);
            if (clazz.isAnnotationPresent(Pet.class)) {
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(PetCase.class)) {
                        Annotation annotation = method.getAnnotation(PetCase.class);
                        PetCase testCaseAnnotation = (PetCase) annotation;
                        if (testCaseAnnotation.enabled()) {
                            petCaseInvokeData.add(new PetCaseInvokeData(method, clazz, testCaseAnnotation));
                        }
                    }
                }
            }
        }
        return petCaseInvokeData;
    }

    private Class getClassForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load PEt test class with name: " + className, e);
        }
    }

    private Set<String> getPetClassesNames() {
        try {
            Set<String> petClassNames = newHashSet();
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            ClassPath classpath = ClassPath.from(loader);
            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses()) {
                if (classInfo.getName().endsWith(PET_CLASS_SUFFIX)) {
                    petClassNames.add(classInfo.getName());
                }
            }
            return petClassNames;
        } catch (IOException e) {
            throw new RuntimeException("Cannot load classPath from loader!", e);
        }
    }


}
