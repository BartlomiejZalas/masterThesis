package com.zalas.masterthesis.apts.pet.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PetCase {

    public boolean enabled() default true;
    public int monitorIntervalInSec() default 60;
    public int durationInSec() default 3600;
    public int delayInSec() default 0;
    public boolean exitOnFailure() default false;

}
