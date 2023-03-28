package com.itmo.online.shop.service;

import com.itmo.online.shop.db.domain.User;
import java.util.List;

public interface UserService {
	
	User findById(Long id);
	
	User findByUsername(String username);
		
	User findByEmail(String email);
		
	void save(User user);
	
	User createUser(String username, String email,  String password, List<String> roles);
}
