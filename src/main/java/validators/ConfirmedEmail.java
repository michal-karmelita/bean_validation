package validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConfirmedEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmedEmail {

    String message() default "Not confirmed e-mail";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
