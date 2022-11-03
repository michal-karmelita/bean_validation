package model;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import validators.AdultStuffOnlyIfAdult;
import validators.BetterAdultStuffOnlyIfAdult;

import java.util.List;

@Getter
@Setter
@AdultStuffOnlyIfAdult
@BetterAdultStuffOnlyIfAdult
public class Order {

    Customer customer;

    List<Product> products;

}
