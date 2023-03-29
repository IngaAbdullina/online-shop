package com.itmo.online.shop.db.entity;

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

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@Column(name = "payment_id")	// todo do we need this?
  private Payment payment;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@Column(name = "shipping_id")	// todo do we need this?
  private Shipping shipping;

  @ManyToOne(fetch = FetchType.LAZY)
//	@Column(name = "user_id")	// todo do we need this?
  private User user;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<CartItem> cartItems;
}
