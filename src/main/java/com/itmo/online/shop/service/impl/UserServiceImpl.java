package com.itmo.online.shop.service.impl;

import com.itmo.online.shop.db.entity.User;
import com.itmo.online.shop.db.Role;
import com.itmo.online.shop.repository.UserRepository;
import com.itmo.online.shop.service.UserService;
import com.itmo.online.shop.util.SecurityUtility;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User findById(Long id) {
    Optional<User> opt = userRepository.findById(id);
    return opt.get();  // todo
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
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  @Transactional
  public User createUser(String username, String password, String email) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(SecurityUtility.passwordEncoder().encode(password));
    user.setEmail(email);
    user.setRole(Role.ROLE_USER);
    return userRepository.save(user);
  }
}
