package com.itmo.online.shop.security;

import com.itmo.online.shop.db.entity.User;
import com.itmo.online.shop.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserSecurityService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("Username not found");
    }
    return new UserSecurityDetails(user);
  }

  public void authenticateUser(String username) {
    UserDetails userDetails = loadUserByUsername(username);
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
        userDetails.getPassword(),
        userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
