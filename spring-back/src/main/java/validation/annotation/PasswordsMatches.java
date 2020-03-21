package validation.annotation;

import validation.validator.PasswordsMatchesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsMatchesValidator.class)
public @interface PasswordsMatches {

    String message() default "{not.matches.password}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
