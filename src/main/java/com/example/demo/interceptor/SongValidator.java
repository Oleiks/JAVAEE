package com.example.demo.interceptor;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SongLengthValidator.class)
public @interface SongValidator {
    String message() default "test";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    double length() default 0.0;
}
