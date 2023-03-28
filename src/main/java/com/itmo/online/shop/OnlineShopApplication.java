package com.itmo.online.shop;

import com.itmo.online.shop.db.DBCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineShopApplication {

  public static void main(String[] args) {
    DBCreator.install();
    SpringApplication.run(OnlineShopApplication.class, args);
  }
}
