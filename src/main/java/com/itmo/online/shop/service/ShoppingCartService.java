package com.itmo.online.shop.service;

import com.itmo.online.shop.db.entity.Article;
import com.itmo.online.shop.db.entity.CartItem;
import com.itmo.online.shop.db.ShoppingCart;
import com.itmo.online.shop.db.entity.User;

public interface ShoppingCartService {

  ShoppingCart getShoppingCart(User user);

  int getItemsNumber(User user);

  CartItem findCartItemById(Long cartItemId);

  CartItem addArticleToShoppingCart(Article article, User user, int qty, String size);

  void clearShoppingCart(User user);

  void updateCartItem(CartItem cartItem, Integer qty);

  void removeCartItem(CartItem cartItem);
}
