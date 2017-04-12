package com.zalas.masterthesis.apt.pet.framework.petcaseprepare;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.reflect.ClassPath.ClassInfo;
import static com.google.common.reflect.ClassPath.from;
import static java.lang.Thread.*;

public class PetClassFinder {

    private String pack4ge;

    public PetClassFinder(String pack4ge) {
        this.pack4ge = pack4ge;
    }

    public Set<String> getPetClassesNames() {
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
