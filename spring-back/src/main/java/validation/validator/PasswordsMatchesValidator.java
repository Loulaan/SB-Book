package validation.validator;

import dto.payload.request.ChangePasswordRequestDTO;
import dto.payload.request.ResetPasswordPostRequestDTO;
import validation.annotation.PasswordsMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.UnexpectedTypeException;

public class PasswordsMatchesValidator implements ConstraintValidator<PasswordsMatches, Object> {

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

        throw new UnexpectedTypeException();
    }

}
