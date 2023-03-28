package com.itmo.online.shop.db.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cart_item")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "qty")
	private int qty;

	@Column(name = "size")
	private String size;
	
	@OneToOne
	@JoinColumn(name="article_id")
//	@Column(name = "article_id")	// todo do we need this?
	private Article article;

	@ManyToOne
	@JoinColumn(name="order_id")
//	@Column(name = "order_id")	// todo do we need this?
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
//	@Column(name = "user_id")	// todo do we need this?
	private User user;

//	public CartItem() {
//	}
	
	public boolean canUpdateQty(Integer qty) {
		return qty == null || qty <= 0 || this.getArticle().hasStock(qty);
	}
	
	public BigDecimal getSubtotal() {
		return new BigDecimal(article.getPrice()).multiply(new BigDecimal(qty));
	}

	public void addQuantity(int qty) {
		if (qty > 0) {
			this.qty = this.qty + qty;
		}
	}
	
	public boolean hasSameSizeThan(String size2) {
		return this.size.equals(size2);
	}
	
	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public int getQty() {
//		return qty;
//	}
//	public void setQty(int qty) {
//		this.qty = qty;
//	}
//	public Article getArticle() {
//		return article;
//	}
//	public void setArticle(Article article) {
//		this.article = article;
//	}
//	public User getUser() {
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
//	public Order getOrder() {
//		return order;
//	}
//	public void setOrder(Order order) {
//		this.order = order;
//	}
//	public String getSize() {
//		return size;
//	}
//	public void setSize(String size) {
//		this.size = size;
//	}

}