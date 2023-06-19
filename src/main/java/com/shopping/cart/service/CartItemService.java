package com.shopping.cart.service;

import com.shopping.cart.model.CartItem;
import com.shopping.cart.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;


    public CartItem save(CartItem cartItem){

        return cartItemRepository.save(cartItem);
    }

}
