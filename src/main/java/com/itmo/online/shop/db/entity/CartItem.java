package com.itmo.online.shop.db.entity;

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
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
//@Getter
//@Setter
@ToString
@Entity
@Table(name = "cart_item")
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

//  @Override
//  public String toString() {
//    return "CartItem{" +
//        "id=" + id +
//        ", quantity=" + quantity +
//        ", size='" + size + '\'' +
//        ", article=" + article +
//        ", order=" + order +
//        ", user=" + user +
//        '}';
//  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "size")
  private String size;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "article_id")
  @ToString.Exclude
  private Article article;

  @ManyToOne
  @JoinColumn(name = "order_id")
  @ToString.Exclude
  private Order order;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @ToString.Exclude
  private User user;

  public boolean canUpdateQuantity(Integer quantity) {
    return quantity == null || quantity <= 0 || this.getArticle().hasStock(quantity);
  }

  public BigDecimal getSubtotal() {
    return BigDecimal.valueOf(article.getPrice()).multiply(new BigDecimal(quantity));
  }

  public void addQuantity(int quantity) {
    if (quantity > 0) {
      this.quantity = this.quantity + quantity;
    }
  }

  public boolean hasSameSizeThan(String size2) {
    return this.size.equals(size2);
  }
}