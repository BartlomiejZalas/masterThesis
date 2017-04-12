package com.zalas.masterthesis.apt.pet.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PetCase {

    public boolean enabled() default true;
    public int monitorIntervalInSec() default 5;
    public int durationInSec() default 30;
    public boolean exitOnFailure() default false;

}
