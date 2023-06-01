package com.shoppyng.service.test;


import com.shoppyng.cart.Application;
import com.shoppyng.cart.model.ShoppingCart;
import com.shoppyng.cart.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {Application.class})
public class ShoppingCartTest {

    @Autowired
    private ShoppingCartService cartService;

    @Test
    public void testInitCartWthCorrectShopAndCustomerCreateAnEmptyCart() throws InterruptedException {
        ShoppingCart cart = cartService.initShoppingCart( 1);
        ShoppingCart retrievIt = cartService.getShoppingCart(cart.getId()).orElseThrow(() -> new NoSuchElementException("cart not found"));
        assertEquals(cart, retrievIt);
    }

    @Test
    public void testAddProductToCart() throws InterruptedException {
        ShoppingCart cart = cartService.initShoppingCart( 1);
        cartService.addProductToCart("prod01", cart.getId(), new BigDecimal(1));
        cartService.addProductToCart("prod01", cart.getId(), new BigDecimal(1));
        cart = cartService.getShoppingCart(cart.getId()).orElseThrow(() -> new NoSuchElementException("cart not found"));
        assertEquals(2, cart.getItems().get(0).getQuantity().intValue());
        // two items of 10 will give a total price  = 20
        assertEquals(20, cart.getTotalPrice().intValue());
    }

    @Test
    public void testRemoveAllProductsFromCartWillUpdateTheTotalPriceToZero() throws InterruptedException {
        ShoppingCart cart = cartService.initShoppingCart( 1);
        cartService.addProductToCart("prod02", cart.getId(), new BigDecimal(3));
        cart = cartService.getShoppingCart(cart.getId()).orElseThrow(() -> new NoSuchElementException("cart not found"));
        assertEquals(3, cart.getItems().get(0).getQuantity().intValue());
        // 3 items of 5 euros will give a total price  = 15
        assertEquals(15, cart.getTotalPrice().intValue());
        // add the prod01 = 10euro
        cartService.addProductToCart("prod01", cart.getId(), new BigDecimal(1));
        assertEquals(25, cart.getTotalPrice().intValue());

        cartService.removeProductFromCart("prod02", cart.getId());
        assertEquals(10, cart.getTotalPrice().intValue());
        cartService.removeProductFromCart("prod01", cart.getId());
        // no more items in the cart
        assertEquals(0, cart.getTotalPrice().intValue());
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    public void addWrongProductReferenceThrowIllegalArgumentException() throws InterruptedException {
        ShoppingCart cart = cartService.initShoppingCart( 1);
        assertThrows(IllegalArgumentException.class, () ->  cartService.addProductToCart("aWrongProduct" , cart.getId(), BigDecimal.ONE));
    }

}