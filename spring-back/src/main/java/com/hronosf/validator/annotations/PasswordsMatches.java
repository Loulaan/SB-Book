package com.hronosf.validator.annotations;


import com.hronosf.validator.impl.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordsMatches {

    String message() default "validation-errors.notMatchesPassword";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
