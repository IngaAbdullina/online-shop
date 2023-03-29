package com.itmo.online.shop.controller;

import com.itmo.online.shop.db.entity.Address;
import com.itmo.online.shop.db.entity.Order;
import com.itmo.online.shop.db.entity.Payment;
import com.itmo.online.shop.db.entity.Shipping;
import com.itmo.online.shop.db.ShoppingCart;
import com.itmo.online.shop.db.entity.User;
import com.itmo.online.shop.service.OrderService;
import com.itmo.online.shop.service.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CheckoutController {

  private final ShoppingCartService shoppingCartService;
  private final OrderService orderService;

  public CheckoutController(ShoppingCartService shoppingCartService,
      OrderService orderService) {
    this.shoppingCartService = shoppingCartService;
    this.orderService = orderService;
  }

  @RequestMapping("/checkout")
  public String checkout(
      @RequestParam(value = "missingRequiredField", required = false) boolean missingRequiredField,
      Model model, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);
    if (shoppingCart.isEmpty()) {
      model.addAttribute("emptyCart", true);
      return "redirect:/shopping-cart/cart";
    }
    model.addAttribute("cartItemList", shoppingCart.getCartItems());
    model.addAttribute("shoppingCart", shoppingCart);
    if (missingRequiredField) {
      model.addAttribute("missingRequiredField", true);
    }
    return "checkout";
  }

  @RequestMapping(value = "/checkout", method = RequestMethod.POST)
  public String placeOrder(@ModelAttribute("shipping") Shipping shipping,
      @ModelAttribute("address") Address address,
      @ModelAttribute("payment") Payment payment,
      RedirectAttributes redirectAttributes, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);
    if (!shoppingCart.isEmpty()) {
      shipping.setAddress(address);
      Order order = orderService.createOrder(shoppingCart, shipping, payment, user);
      redirectAttributes.addFlashAttribute("order", order);
    }
    return "redirect:/order-submitted";
  }

  @RequestMapping(value = "/order-submitted", method = RequestMethod.GET)
  public String orderSubmitted(Model model) {
    Order order = (Order) model.asMap().get("order");
    if (order == null) {
      return "redirect:/";
    }
    model.addAttribute("order", order);
    return "orderSubmitted";
  }

}
