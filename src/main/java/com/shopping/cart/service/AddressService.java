package com.shopping.cart.service;

import com.shopping.cart.model.Address;
import com.shopping.cart.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public void save(Address address) {

        addressRepository.save(address);
    }
}
