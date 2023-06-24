package com.shopping.cart.service;

import com.shopping.cart.model.CartItem;
import com.shopping.cart.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;


    public CartItem save(CartItem cartItem){

        return cartItemRepository.save(cartItem);
    }

}
