package com.shopping.cart.api.mapper;

import com.shopping.cart.api.dto.AddressDTO;
import com.shopping.cart.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {


    public AddressDTO toDTO(Address address) {

        return AddressDTO.builder()
                .id(address.getId())
                .state(address.getState())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .street(address.getStreet())
                .build();
    }
}
