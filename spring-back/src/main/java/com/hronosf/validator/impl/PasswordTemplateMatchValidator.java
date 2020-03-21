package com.hronosf.validator.impl;

import com.hronosf.validator.annotations.PasswordTemplateMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordTemplateMatchValidator implements ConstraintValidator<PasswordTemplateMatch, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$");
    }

}
