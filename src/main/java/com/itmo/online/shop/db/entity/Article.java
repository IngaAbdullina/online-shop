package com.itmo.online.shop.db.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "article")
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "picture")
  private String picture;

  @Column(name = "price")
  private double price;

  @Column(name = "stock")
  private int stock;

  @Column(name = "title")
  private String title;

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Size> sizes;

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Brand> brands;

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Category> categories;

  public boolean hasStock(int amount) {
    return (this.getStock() > 0) && (amount <= this.getStock());
  }

  public void decreaseStock(int amount) {
    this.stock -= amount;
  }

  public void addSize(Size size) {
    sizes.add(size);
    size.setArticle(this);
  }

  public void removeSize(Size size) {
    sizes.remove(size);
    size.setArticle(null);
  }

  public void addCategory(Category category) {
    categories.add(category);
    category.setArticle(this);
  }

  public void removeCategory(Category category) {
    categories.remove(category);
    category.setArticle(null);
  }

  public void addSize(Brand brand) {
    brands.add(brand);
    brand.setArticle(this);
  }

  public void removeSize(Brand brand) {
    brands.remove(brand);
    brand.setArticle(null);
  }
}