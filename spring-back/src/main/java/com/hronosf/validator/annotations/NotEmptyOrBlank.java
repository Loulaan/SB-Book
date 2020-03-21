package com.hronosf.validator.annotations;

import com.hronosf.validator.impl.NotEmptyOrBlankValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyOrBlankValidator.class)
public @interface NotEmptyOrBlank {

    String message() default "validation-errors.notEmptyField";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
