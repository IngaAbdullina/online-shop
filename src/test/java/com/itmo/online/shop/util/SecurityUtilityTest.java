package com.itmo.online.shop.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtilityTest {

  @Test
  public void passwordEncoder_passwordEncodedSuccessfully() {
    BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
    String password = "password123";
    String encodedPassword = passwordEncoder.encode(password);
    assertTrue(passwordEncoder.matches(password, encodedPassword));
  }
}
