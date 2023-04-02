package com.itmo.online.shop.controller;

import com.itmo.online.shop.service.HttpStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiExceptionHandlerTest {

  @Autowired
  private HttpStatusService httpStatusService;

  @Autowired
  private AccountController accountController;

  @Autowired
  private CheckoutController checkoutController;
}
