package com.itmo.online.shop.service.impl;

import com.itmo.online.shop.db.domain.Article;
import com.itmo.online.shop.db.domain.CartItem;
import com.itmo.online.shop.db.domain.Order;
import com.itmo.online.shop.db.domain.Payment;
import com.itmo.online.shop.db.domain.Shipping;
import com.itmo.online.shop.db.domain.ShoppingCart;
import com.itmo.online.shop.db.domain.User;
import com.itmo.online.shop.repository.ArticleRepository;
import com.itmo.online.shop.repository.CartItemRepository;
import com.itmo.online.shop.repository.OrderRepository;
import com.itmo.online.shop.service.OrderService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	private final	OrderRepository orderRepository;
	private final	CartItemRepository cartItemRepository;
	private final	ArticleRepository articleRepository;

	public OrderServiceImpl(OrderRepository orderRepository,
			CartItemRepository cartItemRepository, ArticleRepository articleRepository) {
		this.orderRepository = orderRepository;
		this.cartItemRepository = cartItemRepository;
		this.articleRepository = articleRepository;
	}

	@Override
	@Transactional
	@CacheEvict(value = "itemcount", allEntries = true)
	public synchronized Order createOrder(ShoppingCart shoppingCart, Shipping shipping, Payment payment, User user) {
		Order order = new Order();
		order.setUser(user);
		order.setPayment(payment);
		order.setShipping(shipping);
		order.setOrderTotal(shoppingCart.getGrandTotal());
		shipping.setOrder(order);
		payment.setOrder(order);			
		LocalDate today = LocalDate.now();
		LocalDate estimatedDeliveryDate = today.plusDays(5);				
		order.setOrderDate(Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		order.setShippingDate(Date.from(estimatedDeliveryDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		order.setOrderStatus("In Progress");
		
		order = orderRepository.save(order);
		
		List<CartItem> cartItems = shoppingCart.getCartItems();
		for (CartItem item : cartItems) {
			Article article = item.getArticle();
			article.decreaseStock(item.getQty());
			articleRepository.save(article);
			item.setOrder(order);
			cartItemRepository.save(item);
		}		
		return order;	
	}
	
	@Override
	public Order findOrderWithDetails(Long id) {
		return orderRepository.findEagerById(id);
	}	

	public List<Order> findByUser(User user) {
		return orderRepository.findByUser(user);
	}
}
