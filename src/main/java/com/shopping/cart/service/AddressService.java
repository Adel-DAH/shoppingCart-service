package com.shopping.cart.service;

import com.shopping.cart.model.Address;
import com.shopping.cart.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public void save(Address address) {

        addressRepository.save(address);
    }
}
