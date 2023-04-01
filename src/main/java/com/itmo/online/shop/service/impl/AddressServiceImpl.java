package com.itmo.online.shop.service.impl;

import com.itmo.online.shop.db.entity.Address;
import com.itmo.online.shop.repository.AddressRepository;
import com.itmo.online.shop.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;

  public AddressServiceImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  public Address findById(Long id) {
    return addressRepository.findById(id).get();    // todo
  }

  @Override
  @Transactional
  public Address save(Address address) {
    return addressRepository.save(address);
  }
}