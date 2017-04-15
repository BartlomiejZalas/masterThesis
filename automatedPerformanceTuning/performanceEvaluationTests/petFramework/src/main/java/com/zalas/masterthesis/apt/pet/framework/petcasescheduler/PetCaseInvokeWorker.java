package com.zalas.masterthesis.apt.pet.framework.petcasescheduler;

import com.zalas.masterthesis.apt.pet.framework.assertions.KpiAssertionException;
import com.zalas.masterthesis.apt.pet.framework.petcaseprepare.PetCaseData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class PetCaseInvokeWorker implements Runnable {

    private PetCaseData petCase;
    private Set<String> issues;

    public PetCaseInvokeWorker(PetCaseData petCase, Set<String> issues) {
        this.petCase = petCase;
        this.issues = issues;
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

    private void notifyAboutFailedTestCase(KpiAssertionException e, String methodName) {
        issues.add("Failed testCase: " + methodName + " reason: ?");
        System.out.println("Failed testCase: " + methodName + " reason: " + e.getMessage());
    }
}
