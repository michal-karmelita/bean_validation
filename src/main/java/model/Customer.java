package model;

import gropus.FullValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import payloads.NotConfirmedEmailHandler;
import validators.AddressMinimum;
import validators.ConfirmedEmail;
import validators.Unoccupied;

import java.time.LocalDate;

@Setter
@Getter
public class Customer {

    @Unoccupied(message = "You cannot choose this username")
    String name;

    @NotBlank(message = "{customer.NotBlank}")
    String password;

    @PastOrPresent
    LocalDate birthDate;

    @AddressMinimum(groups = FullValidation.class)
    Address address;

    @ConfirmedEmail(payload = {NotConfirmedEmailHandler.class})
    String email;

}
