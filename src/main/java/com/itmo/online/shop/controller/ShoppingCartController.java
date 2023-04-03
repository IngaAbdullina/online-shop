package com.itmo.online.shop.controller;

import com.itmo.online.shop.db.entity.Article;
import com.itmo.online.shop.db.entity.CartItem;
import com.itmo.online.shop.db.ShoppingCart;
import com.itmo.online.shop.db.entity.User;
import com.itmo.online.shop.exception.ApiException;
import com.itmo.online.shop.service.ArticleService;
import com.itmo.online.shop.service.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

  private final ArticleService articleService;
  private final ShoppingCartService shoppingCartService;

  public ShoppingCartController(
      ArticleService articleService, ShoppingCartService shoppingCartService) {
    this.articleService = articleService;
    this.shoppingCartService = shoppingCartService;
  }

  @RequestMapping("/cart")
  public String shoppingCart(Model model, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);
    model.addAttribute("cartItemList", shoppingCart.getCartItems());
    model.addAttribute("shoppingCart", shoppingCart);
    return "shoppingCart";
  }

  @RequestMapping("/add-item")
  public String addItem(@ModelAttribute("article") Article article, @RequestParam("quantity") String qty,
      @RequestParam("size") String size, RedirectAttributes attributes, Authentication authentication) throws ApiException {
    article = articleService.findArticleById(article.getId());
    if (!article.hasStock(Integer.parseInt(qty))) {
      attributes.addFlashAttribute("notEnoughStock", true);
      return "redirect:/article-detail?id=" + article.getId();
    }
    User user = (User) authentication.getPrincipal();
    shoppingCartService.addArticleToShoppingCart(article, user, Integer.parseInt(qty), size);
    attributes.addFlashAttribute("addArticleSuccess", true);
    return "redirect:/article-detail?id=" + article.getId();
  }

  @RequestMapping("/update-item")
  public String updateItemQuantity(@RequestParam("id") Long cartItemId,
      @RequestParam("quantity") Integer qty, Model model) throws ApiException {
    CartItem cartItem = shoppingCartService.findCartItemById(cartItemId);
    if (cartItem.canUpdateQuantity(qty)) {
      shoppingCartService.updateCartItem(cartItem, qty);
    }
    return "redirect:/shopping-cart/cart";
  }

  @RequestMapping("/remove-item")
  public String removeItem(@RequestParam("id") Long id) throws ApiException {
    shoppingCartService.removeCartItem(shoppingCartService.findCartItemById(id));
    return "redirect:/shopping-cart/cart";
  }
}
