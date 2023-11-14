package com.example.reservationsProject.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleValidatorImpl implements ConstraintValidator<RoleValidator, String> {

    private RoleValidator annotation;

    @Override
    public void initialize(RoleValidator constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // or true, depending on your use case
        }

        Object[] enumValues = annotation.enumClass().getEnumConstants();

        for (Object enumValue : enumValues) {
            if (enumValue.toString().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
