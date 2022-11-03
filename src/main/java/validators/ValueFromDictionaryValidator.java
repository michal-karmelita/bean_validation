package validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValueFromDictionaryValidator implements ConstraintValidator<ValueFromDictionary, Object> {

    String dictionary;

    @Override
    public void initialize(ValueFromDictionary constraintAnnotation) {
        dictionary = constraintAnnotation.dictionary();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return true;
        }

        if ("all_products".equals(dictionary)) {
            return false;
        }
        if ("aveliable_products".equals(dictionary)) {
            return false;
        }
        if ("countries".equals(dictionary)) {
            return "Polska".equals(value);
        }

        return true;
    }
}
