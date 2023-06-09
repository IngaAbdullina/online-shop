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
@Getter
@Setter
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
}