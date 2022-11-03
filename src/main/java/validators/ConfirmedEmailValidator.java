package validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmedEmailValidator implements ConstraintValidator<ConfirmedEmail, String> {

    @Override
    public void initialize(ConfirmedEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String strin, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
