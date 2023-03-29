package com.itmo.online.shop.service;

import com.itmo.online.shop.db.entity.Order;
import com.itmo.online.shop.db.entity.Payment;
import com.itmo.online.shop.db.entity.Shipping;
import com.itmo.online.shop.db.ShoppingCart;
import com.itmo.online.shop.db.entity.User;
import java.util.List;

public interface OrderService {

  Order createOrder(ShoppingCart shoppingCart, Shipping shippingAddress, Payment payment, User user);

  List<Order> findByUser(User user);

  Order findOrderWithDetails(Long id);
}
