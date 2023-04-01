package com.itmo.online.shop.service;

import com.itmo.online.shop.db.entity.Address;

public interface AddressService {

  Address findById(Long id);

  Address save(Address address);
}
