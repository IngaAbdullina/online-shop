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
  @JoinColumn(name = "article_id")
  private Article article;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public boolean canUpdateQty(Integer qty) {
    return qty == null || qty <= 0 || this.getArticle().hasStock(qty);
  }

  public BigDecimal getSubtotal() {
    return BigDecimal.valueOf(article.getPrice()).multiply(new BigDecimal(qty));
  }

  public void addQuantity(int qty) {
    if (qty > 0) {
      this.qty = this.qty + qty;
    }
  }

  public boolean hasSameSizeThan(String size2) {
    return this.size.equals(size2);
  }
}