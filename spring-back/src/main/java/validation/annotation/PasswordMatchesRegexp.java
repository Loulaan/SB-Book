package validation.annotation;

import validation.validator.PasswordMatchesRegexpValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesRegexpValidator.class)
public @interface PasswordMatchesRegexp {

    String message() default "{not.correct.password}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
