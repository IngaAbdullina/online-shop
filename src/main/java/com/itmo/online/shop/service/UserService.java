package com.itmo.online.shop.service;

import com.itmo.online.shop.db.entity.User;
import com.itmo.online.shop.exception.ApiException;

public interface UserService {

  User findById(Long id) throws ApiException;

  User findByUsername(String username);

  User findByEmail(String email);

  void save(User user);

  User createUser(String username, String email, String password);
}
