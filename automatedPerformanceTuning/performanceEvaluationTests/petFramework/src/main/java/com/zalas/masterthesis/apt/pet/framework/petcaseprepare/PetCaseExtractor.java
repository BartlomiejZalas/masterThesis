package com.zalas.masterthesis.apt.pet.framework.petcaseprepare;

import com.zalas.masterthesis.apt.pet.framework.annotations.Pet;
import com.zalas.masterthesis.apt.pet.framework.annotations.PetCase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

public class PetCaseExtractor {

    private PetClassFinder petClassFinder;

    public PetCaseExtractor(PetClassFinder petClassFinder) {
        this.petClassFinder = petClassFinder;
    }

    public Set<PetCaseData> extractPetCasesFromPackage(String p4ckage) {
        return getPetClasses(p4ckage).stream()
                .map(this::extractPetCasesForClass)
                .flatMap(Set::stream)
                .collect(toSet());
    }

    private Set<PetCaseData> extractPetCasesForClass(Class clazz) {
        return stream(clazz.getDeclaredMethods())
                .filter(m -> isPetCase(m) && getPetCaseAnnotation(m).enabled())
                .map(m -> new PetCaseData(m, clazz, getPetCaseAnnotation(m)))
                .collect(toSet());
    }

    private Set<Class> getPetClasses(String p4ckage) {
        return petClassFinder.getPetClassesNames(p4ckage).stream()
                .map(this::getClassForName)
                .filter(this::isPet)
                .collect(toSet());
    }

    private PetCase getPetCaseAnnotation(Method method) {
        Annotation annotation = method.getAnnotation(PetCase.class);
        return (PetCase) annotation;
    }

    private boolean isPetCase(Method method) {
        return method.isAnnotationPresent(PetCase.class);
    }

    private boolean isPet(Class clazz) {
        return clazz.isAnnotationPresent(Pet.class);
    }

    private Class getClassForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load PEt test class with name: " + className, e);
        }
    }

}
