package validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import services.CustomerService;

public class UnoccupiedValidator implements ConstraintValidator<Unoccupied, String> {

    @Override
    public void initialize(Unoccupied constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        CustomerService customerService = new CustomerService();
        return !customerService.isOccupied(value);
    }
}
