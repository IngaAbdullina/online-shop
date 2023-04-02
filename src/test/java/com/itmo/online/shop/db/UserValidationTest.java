package com.itmo.online.shop.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.itmo.online.shop.db.entity.User;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserValidationTest {

  private static Validator validator;

  @BeforeAll
  static void validatorInitializing() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void userValidation_usernameValidationFailed_whenUsernameHasBlankValue() {
    User user = new User();
    user.setUsername("");
    user.setPassword("");
    user.setEmail("");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertFalse(violations.isEmpty());
    violations.forEach(violation -> assertEquals(violation.getMessage(), "не должно быть пустым"));

    user.setUsername(null);
    user.setPassword(null);
    user.setEmail(null);
    violations = validator.validate(user);
    assertFalse(violations.isEmpty());
    violations.forEach(violation -> assertEquals(violation.getMessage(), "не должно быть пустым"));

    user.setUsername("   ");
    user.setPassword("   ");
    user.setEmail("   ");
    violations = validator.validate(user);
    assertFalse(violations.isEmpty());
    violations.forEach(violation -> {
      if (violation.getMessageTemplate().equals("{javax.validation.constraints.NotBlank.message}")) {
        assertEquals(violation.getMessage(), "не должно быть пустым");
      }
      if (violation.getMessageTemplate().equals("{javax.validation.constraints.Email.message}")) {
        assertEquals(violation.getMessage(), "должно иметь формат адреса электронной почты");
      }
    });
  }
}
