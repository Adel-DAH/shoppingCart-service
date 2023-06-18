package com.shopping.cart.api.mapper;

import com.shopping.cart.api.dto.CustomerDTO;
import com.shopping.cart.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDTO toDTO(Customer customer){


        return CustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .addressId(customer.getAddress().getId())
                .shoppingCartId(customer.getShoppingCart().getId())
                .build();

    }
}
