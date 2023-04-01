package com.itmo.online.shop.repository;

import com.itmo.online.shop.db.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
