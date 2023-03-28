package com.itmo.online.shop.repository;

import com.itmo.online.shop.db.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String name);
}
