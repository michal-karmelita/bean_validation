package validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import model.Address;

public class AddressMinimumValidator implements ConstraintValidator<AddressMinimum, Address> {

    @Override
    public void initialize(AddressMinimum constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Address value, ConstraintValidatorContext constraintValidatorContext) {
        if (null != value.getCountry() && "Polska".equalsIgnoreCase(value.getCountry())) {
            return (null != value.getCity() && null != value.getApartmentNr());
        } else {
            return null != value.getCity();
        }
    }
}
