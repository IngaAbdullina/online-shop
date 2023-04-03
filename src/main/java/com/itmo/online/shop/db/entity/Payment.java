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
//@Getter
//@Setter
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCardName() {
    return cardName;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public int getCvc() {
    return cvc;
  }

  public void setCvc(int cvc) {
    this.cvc = cvc;
  }

  public int getExpiryMonth() {
    return expiryMonth;
  }

  public void setExpiryMonth(int expiryMonth) {
    this.expiryMonth = expiryMonth;
  }

  public int getExpiryYear() {
    return expiryYear;
  }

  public void setExpiryYear(int expiryYear) {
    this.expiryYear = expiryYear;
  }

  public String getHolderName() {
    return holderName;
  }

  public void setHolderName(String holderName) {
    this.holderName = holderName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

//  @Override
//  public String toString() {
//    return "Payment{" +
//        "id=" + id +
//        ", cardName='" + cardName + '\'' +
//        ", cardNumber='" + cardNumber + '\'' +
//        ", cvc=" + cvc +
//        ", expiryMonth=" + expiryMonth +
//        ", expiryYear=" + expiryYear +
//        ", holderName='" + holderName + '\'' +
//        ", type='" + type + '\'' +
//        ", order=" + order +
//        '}';
//  }
}