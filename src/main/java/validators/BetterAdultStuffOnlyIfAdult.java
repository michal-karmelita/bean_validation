package validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BetterAdultStuffOnlyIfAdultValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BetterAdultStuffOnlyIfAdult {

    String message() default "The product is only for adult buyers (better)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
