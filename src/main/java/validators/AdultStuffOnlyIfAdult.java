package validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdultStuffOnlyIfAdultValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdultStuffOnlyIfAdult {

    String message() default "The product is only for adult buyers";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
