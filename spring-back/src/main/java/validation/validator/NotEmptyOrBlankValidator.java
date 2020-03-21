package validation.validator;

import validation.annotation.NotEmptyOrBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyOrBlankValidator implements ConstraintValidator<NotEmptyOrBlank, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        return !value.trim().isEmpty();
    }
}
