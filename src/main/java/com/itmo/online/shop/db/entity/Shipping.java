package com.itmo.online.shop.db.entity;

import com.itmo.online.shop.db.entity.Address;
import com.itmo.online.shop.db.entity.Order;
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

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Address getAddress() {
//		return address;
//	}
//
//	public void setAddress(Address address) {
//		this.address = address;
//	}
//
//	public String getReceiver() {
//		return receiver;
//	}
//
//	public void setReceiver(String receiver) {
//		this.receiver = receiver;
//	}
//
//	public Order getOrder() {
//		return order;
//	}
//
//	public void setOrder(Order order) {
//		this.order = order;
//	}

}
