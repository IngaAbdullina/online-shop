package com.itmo.online.shop.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "size")
public class Size implements Comparable<Size> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "value")
  private String value;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "article_id")
  @ToString.Exclude
  private Article article;

  public Size(String value, Article article) {
    this.value = value;
    this.article = article;
  }

  @Override
  public int compareTo(Size s) {
    return this.value.compareTo(s.getValue());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

//  @Override
//  public String toString() {
//    return "Size{" +
//        "id=" + id +
//        ", value='" + value + '\'' +
//        ", article=" + article +
//        '}';
//  }
}