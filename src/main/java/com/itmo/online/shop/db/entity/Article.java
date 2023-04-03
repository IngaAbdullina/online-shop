package com.itmo.online.shop.db.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
//@Getter
//@Setter
@ToString
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

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @ToString.Exclude
  private Set<Size> sizes;

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @ToString.Exclude
  private Set<Brand> brands;

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @ToString.Exclude
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

  public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Set<Size> getSizes() {
		return sizes;
	}
	public void setSizes(Set<Size> sizes) {
		this.sizes = sizes;
	}
	public Set<Brand> getBrands() {
		return brands;
	}
	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}
	public Set<Category> getCategories() {
		return categories;
	}
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

//  @Override
//  public String toString() {
//    return "Article{" +
//        "id=" + id +
//        ", picture='" + picture + '\'' +
//        ", price=" + price +
//        ", stock=" + stock +
//        ", title='" + title + '\'' +
//        ", sizes=" + sizes +
//        ", brands=" + brands +
//        ", categories=" + categories +
//        '}';
//  }
}