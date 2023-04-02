package com.itmo.online.shop.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.itmo.online.shop.db.Role;
import com.itmo.online.shop.db.entity.Address;
import com.itmo.online.shop.db.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSecurityServiceTest {

  @Test
  public void authenticateUser_userAuthenticatedSuccessfully() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    UserDetails userDetails = new User(0L, "username", "password", "email@email.com",
        "test first name", "test last name", new Address(), Role.ROLE_USER);
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
        userDetails.getPassword(),
        userDetails.getAuthorities());

    securityContext.setAuthentication(authentication);
    assertNotNull(securityContext.getAuthentication());
  }
}
