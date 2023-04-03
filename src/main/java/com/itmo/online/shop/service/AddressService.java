package com.itmo.online.shop.service;

import com.itmo.online.shop.db.entity.Address;
import com.itmo.online.shop.exception.ApiException;

public interface AddressService {

  Address findById(Long id) throws ApiException;

  Address save(Address address);
}
