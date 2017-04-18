package com.zalas.masterthesis.apts.pet.framework.petcasescheduler;

import com.zalas.masterthesis.apts.pet.framework.PerformanceIssue;
import com.zalas.masterthesis.apts.pet.framework.PerformanceIssues;
import com.zalas.masterthesis.apts.pet.framework.assertions.KpiAssertionException;
import com.zalas.masterthesis.apts.pet.framework.petcaseprepare.PetCaseData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PetCaseInvokeWorker implements Runnable {

    private PetCaseData petCase;
    private PerformanceIssues performanceIssues;

    public PetCaseInvokeWorker(PetCaseData petCase, PerformanceIssues performanceIssues) {
        this.petCase = petCase;
        this.performanceIssues = performanceIssues;
    }

    @Override
    public void run() {
        executePetCase(petCase.getPetClass(), petCase.getPetCaseMethod());
    }

    private void executePetCase(Class clazz, Method method) {
        try {
            method.invoke(clazz.newInstance());
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Cannot invoke PET case: " + method.getName(), e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof KpiAssertionException) {
                notifyAboutFailedTestCase((KpiAssertionException) e.getCause(), method.getName());
            } else {
                throw new RuntimeException("Cannot invoke PET case: " + method.getName(), e);
            }
        }
    }

    private void notifyAboutFailedTestCase(KpiAssertionException e, String testName) {
        performanceIssues.add(new PerformanceIssue(testName, e.getKpiName(), e.getReason(), e.getPercentageDeviation(), e.getMessage()));
        System.out.println("Failed testCase: " + testName + " reason: " + e.getMessage());
    }
}
