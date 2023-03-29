package com.itmo.online.shop.db.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "shipping")
public class Shipping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "receiver")
	private String receiver;

	@OneToOne(cascade= CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "address_id")
//	@Column(name = "address_id")	// todo do we need this?
	private Address address;

	@OneToOne
//	@Column(name = "order_id")	// todo do we need this?
	private Order order;
}