package validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import model.Order;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BetterAdultStuffOnlyIfAdultValidator implements ConstraintValidator<BetterAdultStuffOnlyIfAdult, Order> {

    @Override
    public void initialize(BetterAdultStuffOnlyIfAdult constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Order value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value.getCustomer() || null == value.getCustomer().getBirthDate() || null == value.getProducts()) {
            return true;
        }
        if (LocalDate.from(value.getCustomer().getBirthDate()).until(LocalDate.now(), ChronoUnit.YEARS) < 18) {
            boolean valid = true;

            for (int i = 0; i < value.getProducts().size(); i++) {
                if (null != value.getProducts().get(i) && value.getProducts().get(i).isForAdults()) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("The product is only for adult buyers (better)")
                            .addPropertyNode("products[" + i + "]")
                            .addConstraintViolation()
                            .disableDefaultConstraintViolation();
                    valid = false;
                }
            }
            return valid;
        }
        return true;
    }
}
