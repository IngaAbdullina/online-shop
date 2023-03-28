package com.itmo.online.shop.service;

import com.itmo.online.shop.db.domain.Order;
import com.itmo.online.shop.db.domain.Payment;
import com.itmo.online.shop.db.domain.Shipping;
import com.itmo.online.shop.db.domain.ShoppingCart;
import com.itmo.online.shop.db.domain.User;
import java.util.List;

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, Shipping shippingAddress, Payment payment, User user);
	
	List<Order> findByUser(User user);
	
	Order findOrderWithDetails(Long id);
}
