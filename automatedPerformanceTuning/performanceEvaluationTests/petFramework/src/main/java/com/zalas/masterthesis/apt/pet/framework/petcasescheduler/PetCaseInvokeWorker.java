package com.zalas.masterthesis.apt.pet.framework.petcasescheduler;

import com.zalas.masterthesis.apt.pet.framework.petcaseprepare.PetCaseData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PetCaseInvokeWorker implements Runnable {

    private PetCaseData petCase;

    public PetCaseInvokeWorker(PetCaseData petCase) {
        this.petCase = petCase;
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
            if (e.getCause() instanceof AssertionError) {
                notifyAboutFailedTestCase((AssertionError) e.getCause(), method.getName());
            } else {
                throw new RuntimeException("Cannot invoke PET case: " + method.getName(), e);
            }
        }
    }

    private void notifyAboutFailedTestCase(AssertionError e, String methodName) {
        System.out.println("Failed testCase: " + methodName + " reason: " + e.getMessage());
    }
}
