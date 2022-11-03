package model;

import lombok.Getter;
import lombok.Setter;
import validators.ValueFromDictionary;

@Getter
@Setter
public class Address {

    String street;

    String houseNr;

    String apartmentNr;

    String postalCode;

    String city;

    @ValueFromDictionary(dictionary = "countries", message = "Invalid country name")
    String country;

}
