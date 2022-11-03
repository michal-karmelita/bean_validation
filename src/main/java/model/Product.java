package model;

import gropus.BasicValidation;
import gropus.FullValidation;
import lombok.Getter;
import lombok.Setter;
import validators.ValueFromDictionary;

import java.math.BigDecimal;

@Setter
@Getter
public class Product {

    @ValueFromDictionary(dictionary = "aveliable_products", groups = FullValidation.class, message = "Product unaveliable")
    @ValueFromDictionary(dictionary = "all_products", groups = BasicValidation.class, message = "Product doesn't exist")
    Integer id;

    String name;

    BigDecimal price;

    boolean forAdults;

}
