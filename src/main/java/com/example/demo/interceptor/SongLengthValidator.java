package com.example.demo.interceptor;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SongLengthValidator implements ConstraintValidator<SongValidator, Double> {
    private double length;

    @Override
    public void initialize(SongValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        length = constraintAnnotation.length();
    }

    @Override
    public boolean isValid(Double length, ConstraintValidatorContext context) {
        return (length != null && length >= this.length);
    }
}
