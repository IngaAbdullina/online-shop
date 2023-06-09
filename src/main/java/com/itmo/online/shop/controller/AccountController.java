package com.itmo.online.shop.controller;

import com.itmo.online.shop.db.entity.Address;
import com.itmo.online.shop.db.entity.Order;
import com.itmo.online.shop.db.entity.User;
import com.itmo.online.shop.exception.ApiException;
import com.itmo.online.shop.exception.ErrorCode;
import com.itmo.online.shop.security.UserSecurityService;
import com.itmo.online.shop.service.AddressService;
import com.itmo.online.shop.service.OrderService;
import com.itmo.online.shop.service.UserService;
import com.itmo.online.shop.util.SecurityUtility;
import java.security.Principal;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Validated
public class AccountController {

  private final UserService userService;
  private final UserSecurityService userSecurityService;
  private final OrderService orderService;
  private final AddressService addressService;

  public AccountController(UserService userService, UserSecurityService userSecurityService,
      OrderService orderService, AddressService addressService) {
    this.userService = userService;
    this.userSecurityService = userSecurityService;
    this.orderService = orderService;
    this.addressService = addressService;
  }

  @RequestMapping("/login")
  public String log(Model model) {
    model.addAttribute("usernameExists", model.asMap().get("usernameExists"));
    model.addAttribute("emailExists", model.asMap().get("emailExists"));
    return "myAccount";
  }

  @RequestMapping("/my-profile")
  public String myProfile(Model model, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    model.addAttribute("user", user);
    return "myProfile";
  }

  @RequestMapping("/my-orders")
  public String myOrders(Model model, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    model.addAttribute("user", user);
    List<Order> orders = orderService.findByUser(user);
    model.addAttribute("orders", orders);
    return "myOrders";
  }

  @RequestMapping("/my-address")
  public String myAddress(Model model, Principal principal) {
    User user = userService.findByUsername(principal.getName());
    model.addAttribute("user", user);
    return "myAddress";
  }

  @PostMapping(value = "/update-user-address")
  public String updateUserAddress(@ModelAttribute("address") Address address,
      Principal principal) throws Exception {
    User currentUser = userService.findByUsername(principal.getName());
    if (currentUser == null) {
      throw new ApiException(ErrorCode.NOT_FOUND,
          String.format("User %s not found", principal.getName()));
    }
    address = addressService.save(address);
    currentUser.setAddress(address);
    userService.save(currentUser);
    return "redirect:/my-address";
  }

  @PostMapping(value = "/new-user")
  public String newUserPost(@ModelAttribute("user") User user, BindingResult bindingResults,
      @ModelAttribute("new-password") String password, RedirectAttributes redirectAttributes,
      Model model) {
    model.addAttribute("email", user.getEmail());
    model.addAttribute("username", user.getUsername());
    user.setUsername(user.getUsername().trim());
    user.setPassword(user.getPassword().trim());
    user.setEmail(user.getEmail().trim());

    boolean invalidFields = false;
    if (bindingResults.hasErrors()) {
      return "redirect:/login";
    }

    if (userService.findByUsername(user.getUsername()) != null) {
      redirectAttributes.addFlashAttribute("usernameExists", true);
      invalidFields = true;
    }

    if (userService.findByEmail(user.getEmail()) != null) {
      redirectAttributes.addFlashAttribute("emailExists", true);
      invalidFields = true;
    }

    if (invalidFields) {
      return "redirect:/login";
    }

    user = userService.createUser(user.getUsername(), password, user.getEmail());
    userSecurityService.authenticateUser(user.getUsername());
    return "redirect:/my-profile";
  }

  @PostMapping(value = "/update-user-info")
  public String updateUserInfo(@ModelAttribute("user") User user,
      @RequestParam("newPassword") String newPassword,
      Model model, Principal principal) throws Exception {
    user.setUsername(user.getUsername().trim());
    user.setPassword(user.getPassword().trim());
    user.setEmail(user.getEmail().trim());

    User currentUser = userService.findByUsername(principal.getName());
    if (currentUser == null) {
      throw new ApiException(ErrorCode.NOT_FOUND,
          String.format("User %s not found", principal.getName()));
    }

    User existingUser = userService.findByUsername(user.getUsername());
    if (existingUser != null && !existingUser.getId().equals(currentUser.getId())) {
      model.addAttribute("usernameExists", true);
      return "myProfile";
    }

    existingUser = userService.findByEmail(user.getEmail());
    if (existingUser != null && !existingUser.getId().equals(currentUser.getId())) {
      model.addAttribute("emailExists", true);
      return "myProfile";
    }

    if (StringUtils.isNotBlank(newPassword)) {
      BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
      String dbPassword = currentUser.getPassword();
      if (passwordEncoder.matches(user.getPassword(), dbPassword)) {
        currentUser.setPassword(passwordEncoder.encode(newPassword));
      } else {
        model.addAttribute("incorrectPassword", true);
        return "myProfile";
      }
    }
    currentUser.setFirstName(user.getFirstName());
    currentUser.setLastName(user.getLastName());
    currentUser.setUsername(user.getUsername());
    currentUser.setEmail(user.getEmail());
    userService.save(currentUser);

    model.addAttribute("updateSuccess", true);
    model.addAttribute("user", currentUser);
    userSecurityService.authenticateUser(currentUser.getUsername());
    return "myProfile";
  }

  @RequestMapping("/order-detail")
  public String orderDetail(@RequestParam("order") Long id, Model model) throws ApiException {
    Order order = orderService.findOrderWithDetails(id);
    model.addAttribute("order", order);
    return "orderDetails";
  }
}