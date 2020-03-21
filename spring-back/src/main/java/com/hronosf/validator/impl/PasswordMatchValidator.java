package com.hronosf.validator.impl;

import com.hronosf.dto.requests.ChangePasswordRequestDTO;
import com.hronosf.dto.requests.ResetPasswordPostRequestDTO;
import com.hronosf.validator.annotations.PasswordsMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordsMatches, Object> {

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        if (object instanceof ChangePasswordRequestDTO) {
            ChangePasswordRequestDTO request = (ChangePasswordRequestDTO) object;
            return request.getNewPassword().equals(request.getConfirmPassword());
        }

        if (object instanceof ResetPasswordPostRequestDTO) {
            ResetPasswordPostRequestDTO request = (ResetPasswordPostRequestDTO) object;
            return request.getPassword().equals(request.getConfirmPassword());
        }

        return false;
    }
}
