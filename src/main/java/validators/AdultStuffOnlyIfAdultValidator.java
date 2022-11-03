package validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import model.Order;
import model.Product;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class AdultStuffOnlyIfAdultValidator implements ConstraintValidator<AdultStuffOnlyIfAdult, Order> {

    @Override
    public void initialize(AdultStuffOnlyIfAdult constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Order value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value.getCustomer() || null == value.getCustomer().getBirthDate()) {
            return true;
        }
        if (LocalDate.from(value.getCustomer().getBirthDate()).until(LocalDate.now(), ChronoUnit.YEARS) < 18
                && Optional.ofNullable(value.getProducts()).map(products -> products.stream()
                        .anyMatch(Product::isForAdults))
                .orElse(false)) {
            return false;
        }
        return true;
    }
}
