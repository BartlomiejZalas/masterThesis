package com.zalas.masterthesis.apt.pet.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PetCaseWorker implements Runnable {

    private PetCaseInvokeData petCase;

    public PetCaseWorker(PetCaseInvokeData petCase) {
        this.petCase = petCase;
    }

    @Override
    public void run() {
        System.out.println("Start " + petCase.getPetCaseMethod().getName());
        try {
            executePetCase(petCase.getPetClass(), petCase.getPetCaseMethod());
        } catch (Exception e) {
            System.out.println("jednak cośsię przedarło");
        }
        System.out.println( petCase.getPetCaseMethod().getName() + " end after "+petCase.getTestCaseAnnotation().monitorIntervalInSec() +"sec.");
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
        System.out.println("Failed testCase: " +methodName + " reason: " + e.getMessage());
    }
}
