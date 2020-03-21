package com.hronosf.validator.impl;

import com.hronosf.validator.annotations.NotEmptyOrBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyOrBlankValidator implements ConstraintValidator<NotEmptyOrBlank, String> {
    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        if (field == null) {
            return false;
        }
        return !field.replaceAll("\\s+"," ").trim().isEmpty();
    }
}
