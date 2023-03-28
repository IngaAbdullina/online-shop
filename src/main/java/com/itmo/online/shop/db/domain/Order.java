package com.itmo.online.shop.db.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "order_date")
	private Date orderDate;

	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "order_total")
	private BigDecimal orderTotal;

	@Column(name = "shipping_date")
	private Date shippingDate;

	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
//	@Column(name = "payment_id")	// todo do we need this?
	private Payment payment;

	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
//	@Column(name = "shipping_id")	// todo do we need this?
	private Shipping shipping;

	@ManyToOne(fetch = FetchType.LAZY)
//	@Column(name = "user_id")	// todo do we need this?
	private User user;

	@OneToMany(mappedBy="order", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CartItem> cartItems;

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Date getOrderDate() {
//		return orderDate;
//	}
//
//	public void setOrderDate(Date orderDate) {
//		this.orderDate = orderDate;
//	}
//
//	public Date getShippingDate() {
//		return shippingDate;
//	}
//
//	public void setShippingDate(Date shippingDate) {
//		this.shippingDate = shippingDate;
//	}
//
//	public String getOrderStatus() {
//		return orderStatus;
//	}
//
//	public void setOrderStatus(String orderStatus) {
//		this.orderStatus = orderStatus;
//	}
//
//	public BigDecimal getOrderTotal() {
//		return orderTotal;
//	}
//
//	public void setOrderTotal(BigDecimal orderTotal) {
//		this.orderTotal = orderTotal;
//	}
//
//	public List<CartItem> getCartItems() {
//		return cartItems;
//	}
//
//	public void setCartItems(List<CartItem> cartItems) {
//		this.cartItems = cartItems;
//	}
//
//	public Shipping getShipping() {
//		return shipping;
//	}
//
//	public void setShipping(Shipping shipping) {
//		this.shipping = shipping;
//	}
//
//	public Payment getPayment() {
//		return payment;
//	}
//
//	public void setPayment(Payment payment) {
//		this.payment = payment;
//	}
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
	
	
}
