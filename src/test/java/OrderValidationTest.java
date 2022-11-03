import gropus.FullValidation;
import jakarta.validation.*;
import model.Address;
import model.Customer;
import model.Order;
import model.Product;
import org.junit.jupiter.api.Test;
import payloads.NotConfirmedEmailHandler;
import util.DBMessageInterpolator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrderValidationTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();


    @Test
    void whenNameIsOccupiedThenViolation() {
        Customer customer = new Customer();
        customer.setName("Michal"); //zajęte

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()), hasItem("You cannot choose this username"));
    }

    @Test
    void whenNameIsUnoccupiedThenThereIsNoViolation() {
        Customer customer = new Customer();
        customer.setName("Michal2"); //niezajęte

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()), not(hasItem("You cannot choose this username")));
    }

    @Test
    void whenNoCityNameThenViolation() {
        Customer customer = new Customer();
        Address address = new Address();
        customer.setAddress(address);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer, FullValidation.class);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()), hasItem("The address minimum is not filled"));
    }

    @Test
    void whenNoCityNameThenNoViolationInNoFullValidation() {
        Customer customer = new Customer();
        Address address = new Address();
        customer.setAddress(address);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()), not(hasItem("The address minimum is not filled")));
    }

    @Test
    void whenValueNotFromDictionaryThenViolation() {
        Address address = new Address();
        address.setCountry("San Escobar");

        Set<ConstraintViolation<Address>> violations = validator.validate(address);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()), hasItem("Invalid country name"));
    }

    @Test
    void whenValueFromDictionaryThenNoViolation() {
        Address address = new Address();
        address.setCountry("Polska");

        Set<ConstraintViolation<Address>> violations = validator.validate(address);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()), not(hasItem("Invalid country name")));
    }

    @Test
    void whenProductNotAveliableAndFullValidationThenProperViolation() {
        Product product = new Product();
        product.setId(123);

        Set<ConstraintViolation<Product>> violations = validator.validate(product, FullValidation.class);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()), hasItem("Product unaveliable"));

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()), not(hasItem("Product doesn't exist")));
    }

    @Test
    void messageFromFileWorks() {
        Customer customer = new Customer();
        customer.setPassword("");

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()), hasItem("Password cannot be empty"));
    }

    @Test
    void messageFromDBWorks() {
        Customer customer = new Customer();
        customer.setPassword("");

        ValidatorFactory factory = Validation.byDefaultProvider().configure().messageInterpolator(new DBMessageInterpolator()).buildValidatorFactory();
        Validator localValidator = factory.getValidator();

        Set<ConstraintViolation<Customer>> violations = localValidator.validate(customer);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()), hasItem("Message from data base"));
    }

    @Test
    void ifUnder18AndBuyingAdultStuffThenViolation() {
        Customer customer = new Customer();
        customer.setBirthDate(LocalDate.now().minusYears(15));
        Product product = new Product();
        product.setForAdults(true);
        List<Product> products = List.of(product);
        Order order = new Order();
        order.setProducts(products);
        order.setCustomer(customer);

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()), hasItem("The product is only for adult buyers"));
        String location = violations.stream()
                .filter(violation -> "The product is only for adult buyers".equals(violation.getMessage()))
                .map(ConstraintViolation::getPropertyPath)
                .map(Path::toString)
                .findFirst().orElse("not found");
        assertThat(location, equalTo(""));
    }

    @Test
    void ifUnder18AndBuyingAdultStuffThenBetterViolation() {
        Customer customer = new Customer();
        customer.setBirthDate(LocalDate.now().minusYears(15));
        Product product = new Product();
        product.setForAdults(true);
        List<Product> products = List.of(product);
        Order order = new Order();
        order.setProducts(products);
        order.setCustomer(customer);

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertThat(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()), hasItem("The product is only for adult buyers (better)"));
        String location = violations.stream()
                .filter(violation -> "The product is only for adult buyers (better)".equals(violation.getMessage()))
                .map(ConstraintViolation::getPropertyPath)
                .map(Path::toString)
                .findFirst().orElse("not found");
        assertThat(location, equalTo("products[0]"));
    }

    @Test
    void whenViolatedConfirmedEMailThenPayload() {

        Customer customer = new Customer();
        customer.setEmail("ddd@aa.pl");

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        violations.forEach(orderConstraintViolation -> {
            Set<Class<? extends Payload>> payload =
                    orderConstraintViolation.getConstraintDescriptor().getPayload();

            payload.forEach(aClass -> {
                if (NotConfirmedEmailHandler.class.isAssignableFrom(aClass)) {
                    try {
                        NotConfirmedEmailHandler notConfirmedEmailHandler = (NotConfirmedEmailHandler) aClass.getDeclaredConstructor().newInstance();
                        notConfirmedEmailHandler.handleSendingEmail();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        });
    }


}
