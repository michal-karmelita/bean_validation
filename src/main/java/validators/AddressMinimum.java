package validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AddressMinimumValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AddressMinimum {

    String message() default "The address minimum is not filled";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
