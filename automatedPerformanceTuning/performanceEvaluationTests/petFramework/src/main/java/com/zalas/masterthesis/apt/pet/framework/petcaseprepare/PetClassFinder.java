package com.zalas.masterthesis.apt.pet.framework.petcaseprepare;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.reflect.ClassPath.ClassInfo;
import static com.google.common.reflect.ClassPath.from;
import static java.lang.Thread.*;

public class PetClassFinder {

    public Set<String> getPetClassesNames(String pack4ge) {
        Set<String> petClassNames = newHashSet();
        for (ClassInfo classInfo : getClassPath().getTopLevelClasses(pack4ge)) {
            petClassNames.add(classInfo.getName());
        }
        return petClassNames;
    }

    private ClassPath getClassPath() {
        try {
            return from(currentThread().getContextClassLoader());
        } catch (IOException e) {
            throw new RuntimeException("Cannot load classPath from loader!", e);
        }
    }
}
