package com.zalas.masterthesis.apt.pet.framework;

import java.lang.reflect.Method;

class PetCaseInvokeData {
    private Method petCaseMethod;
    private Class petClass;
    private PetCase testCaseAnnotation;

    public PetCaseInvokeData(Method petCaseMethod, Class petClass, PetCase testCaseAnnotation) {
        this.petCaseMethod = petCaseMethod;
        this.petClass = petClass;
        this.testCaseAnnotation = testCaseAnnotation;
    }

    public Method getPetCaseMethod() {
        return petCaseMethod;
    }

    public Class getPetClass() {
        return petClass;
    }

    public PetCase getTestCaseAnnotation() {
        return testCaseAnnotation;
    }
}
