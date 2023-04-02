package com.itmo.online.shop.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.itmo.online.shop.db.entity.Address;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AddressValidationTest {

  private static Validator validator;

  @BeforeAll
  static void validatorInitializing() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void userValidation_usernameValidationFailed_whenUsernameHasBlankValue() {
    Address address = new Address();
    address.setCountry("");
    address.setCity("");
    address.setStreetAddress("");
    address.setZipCode("");

    Set<ConstraintViolation<Address>> violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    violations.forEach(violation -> assertEquals(violation.getMessage(), "не должно быть пустым"));

    address.setCountry("     ");
    address.setCity("      ");
    address.setStreetAddress("       ");
    address.setZipCode("      ");

    violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    violations.forEach(violation -> assertEquals(violation.getMessage(), "не должно быть пустым"));

    address.setCountry(null);
    address.setCity(null);
    address.setStreetAddress(null);
    address.setZipCode(null);

    violations = validator.validate(address);
    assertFalse(violations.isEmpty());
    violations.forEach(violation -> assertEquals(violation.getMessage(), "не должно быть пустым"));
  }
}
