package com.shopping.cart.api.mapper;

import com.shopping.cart.api.dto.ShoppingCartDTO;
import com.shopping.cart.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class ShoppingCartMapper {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    public ShoppingCartDTO toDTO(ShoppingCart shoppingCart) {

        return ShoppingCartDTO.builder()
                .id(shoppingCart.getId())
                .creationDate(shoppingCart.getCreationDate())
                .totalPriceWithVAT(shoppingCart.getTotalPriceWithVAT())
                .totalPrice(shoppingCart.getTotalPrice())
                .customer(customerMapper.toDTO(shoppingCart.getCustomer()))
                .items(shoppingCart.getItems()
                        .stream().map(cartItem -> cartItemMapper.toDTO(cartItem)).collect(Collectors.toList()))
                .build();

    }

}
