package com.hronosf.validator.annotations;

import com.hronosf.validator.impl.PasswordTemplateMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordTemplateMatchValidator.class)
public @interface PasswordTemplateMatch {

    String message() default "validation-errors.notMatchPasswordRegexp";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
