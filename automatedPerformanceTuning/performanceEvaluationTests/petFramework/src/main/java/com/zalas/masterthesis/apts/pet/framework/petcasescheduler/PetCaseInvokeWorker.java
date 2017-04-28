package com.zalas.masterthesis.apts.pet.framework.petcasescheduler;

import com.zalas.masterthesis.apts.pet.framework.PerformanceIssueTO;
import com.zalas.masterthesis.apts.pet.framework.PerformanceIssues;
import com.zalas.masterthesis.apts.pet.framework.assertions.PerformanceIssue;
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
            if (e.getCause() instanceof PerformanceIssue) {
                notifyAboutNewIssue((PerformanceIssue) e.getCause());
            } else {
                throw new RuntimeException("Cannot invoke PET case: " + method.getName(), e);
            }
        }
    }

    private void notifyAboutNewIssue(PerformanceIssue e) {
        performanceIssues.add(new PerformanceIssueTO(e.getMetric(), e.getReason()));
        System.out.println("Failed testCase: " + e.getMetric() + " reason: " + e.getReason());
    }
}
