package com.itmo.online.shop.service.impl;

import com.itmo.online.shop.db.entity.Address;
import com.itmo.online.shop.repository.AddressRepository;
import com.itmo.online.shop.service.AddressService;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;

  public AddressServiceImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  public Address findById(Long id) {
//    return addressRepository.getReferenceById(id);
    Optional<Address> opt = addressRepository.findById(id);
    return opt.get();
  }

  @Override
  public Address save(Address address) {
    address.setCountry(address.getCountry().trim());
    address.setCity(address.getCity().trim());
    address.setStreetAddress(address.getStreetAddress().trim());
    address.setZipCode(address.getZipCode().trim());
    return addressRepository.save(address);
  }
}
