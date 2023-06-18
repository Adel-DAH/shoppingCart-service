package com.shopping.cart.api.mapper;


import com.shopping.cart.api.dto.CartItemDTO;
import com.shopping.cart.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {


    public CartItemDTO toDTO(CartItem cartItem) {

        return CartItemDTO.builder()
                .id(cartItem.getId())
                .code(cartItem.getCode())
                .name(cartItem.getName())
                .description(cartItem.getDescription())
                .calculatedPrice(cartItem.getCalculatedPrice())
                .shoppingCartId(cartItem.getShoppingCart().getId())
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getUnitPrice())
                .build();
    }
}
