package com.zalas.masterthesis.apt.pet.framework.petcaseprepare;

import com.zalas.masterthesis.apt.pet.framework.annotations.PetCase;

import java.lang.reflect.Method;

public class PetCaseData {
    private Method petCaseMethod;
    private Class petClass;
    private boolean isEnabled;
    private int monitorIntervalInSec;
    private int durationInSec;
    private boolean exitOnFailure;

    public PetCaseData(Method petCaseMethod, Class petClass, PetCase testCaseAnnotation) {
        this.petCaseMethod = petCaseMethod;
        this.petClass = petClass;
        this.isEnabled = testCaseAnnotation.enabled();
        this.monitorIntervalInSec = testCaseAnnotation.monitorIntervalInSec();
        this.durationInSec = testCaseAnnotation.durationInSec();
        this.exitOnFailure = testCaseAnnotation.exitOnFailure();
    }

    public Method getPetCaseMethod() {
        return petCaseMethod;
    }

    public Class getPetClass() {
        return petClass;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
    public int getMonitorIntervalInSec() {
        return monitorIntervalInSec;
    }
    public int getDurationInSec() {
        return durationInSec;
    }
    public boolean getExitOnFailure() {
        return exitOnFailure;
    }
}