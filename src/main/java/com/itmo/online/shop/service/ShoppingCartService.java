package com.itmo.online.shop.service;

import com.itmo.online.shop.db.domain.Article;
import com.itmo.online.shop.db.domain.CartItem;
import com.itmo.online.shop.db.domain.ShoppingCart;
import com.itmo.online.shop.db.domain.User;

public interface ShoppingCartService {

	ShoppingCart getShoppingCart(User user);
	
	int getItemsNumber(User user);
	
	CartItem findCartItemById(Long cartItemId);
	
	CartItem addArticleToShoppingCart(Article article, User user, int qty, String size);
		
	void clearShoppingCart(User user);
	
	void updateCartItem(CartItem cartItem, Integer qty);

	void removeCartItem(CartItem cartItem);
}
