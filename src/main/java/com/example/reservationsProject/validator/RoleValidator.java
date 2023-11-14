package com.example.reservationsProject.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Documented
@Constraint(validatedBy = RoleValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleValidator {
    Class<? extends Enum<?>> enumClass();

    String message() default "Invalid value. Must be one of: {validValues}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
