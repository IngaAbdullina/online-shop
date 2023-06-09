package com.itmo.online.shop.service.impl;

import com.itmo.online.shop.db.entity.User;
import com.itmo.online.shop.db.Role;
import com.itmo.online.shop.exception.ApiException;
import com.itmo.online.shop.exception.ErrorCode;
import com.itmo.online.shop.repository.UserRepository;
import com.itmo.online.shop.service.UserService;
import com.itmo.online.shop.util.SecurityUtility;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User findById(Long id) throws ApiException {
    Optional<User> optionalUser = userRepository.findById(id);
    if (!optionalUser.isPresent()) {
      throw new ApiException(
          ErrorCode.NOT_FOUND, String.format("User [id=%s] not found", id));
    }
    return optionalUser.get();
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  @Transactional
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public User createUser(String username, String password, String email) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(SecurityUtility.passwordEncoder().encode(password));
    user.setEmail(email);
    user.setRole(Role.ROLE_USER);
    return userRepository.save(user);
  }
}
