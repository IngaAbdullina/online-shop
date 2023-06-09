package com.itmo.online.shop.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
@Table(name = "payment")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "card_name")
  private String cardName;

  @Column(name = "card_number")
  private String cardNumber;

  @Column(name = "cvc")
  private int cvc;

  @Column(name = "expiry_month")
  private int expiryMonth;

  @Column(name = "expiry_year")
  private int expiryYear;

  @Column(name = "holder_name")
  private String holderName;

  @Column(name = "type")
  private String type;

  @OneToOne(fetch = FetchType.EAGER)
  @ToString.Exclude
  private Order order;
}