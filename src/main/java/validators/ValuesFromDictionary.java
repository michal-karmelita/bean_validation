package validators;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface ValuesFromDictionary {

    ValueFromDictionary[] value();

}
