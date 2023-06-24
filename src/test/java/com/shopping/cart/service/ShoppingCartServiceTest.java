package com.shopping.cart.service;

import com.shopping.cart.api.dto.ShoppingCartDTO;

import com.shopping.cart.model.Product;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.math.BigDecimal;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService cartService;

    @MockBean
    private ProductService productService;

   @BeforeEach
   void init(){

       Product expectedProduct1 = new Product();
       expectedProduct1.setName("Milka");
       expectedProduct1.setReference("prod01");
       expectedProduct1.setDescription("this is our Milka description");
       expectedProduct1.setPrice(new BigDecimal(10));

       Product expectedProduct2 = new Product();
       expectedProduct2.setName("Cheese");
       expectedProduct2.setReference("prod02");
       expectedProduct2.setDescription("this is our Cheese description");
       expectedProduct2.setPrice(new BigDecimal(5));

       Mockito.when(productService.getProduct("prod01")).thenReturn(Optional.of(expectedProduct1));
       Mockito.when(productService.getProduct("prod02")).thenReturn(Optional.of(expectedProduct2));

   }


    @Test
    @Transactional
    public void testInitCartWthCorrectShopAndCustomerCreateAnEmptyCart() throws InterruptedException {
        ShoppingCartDTO cart = cartService.initShoppingCart(1);
        ShoppingCartDTO retrievIt = cartService.getShoppingCart(cart.getId()).orElseThrow(() -> new NoSuchElementException("cart not found"));
        assertEquals(cart, retrievIt);
    }

    @Test
    @Transactional
    public void testInitCartWithUnknownCustomer() throws InterruptedException {

        assertThrows(IllegalArgumentException.class, () -> cartService.initShoppingCart(2));

    }


    @Test
    @Transactional
    public void testAddProductToCart() throws InterruptedException {

        ShoppingCartDTO cart = cartService.initShoppingCart(1);
        cartService.addProductToCart("prod01", cart.getId(), new BigDecimal(1));
        cartService.addProductToCart("prod01", cart.getId(), new BigDecimal(1));
        cart = cartService.getShoppingCart(cart.getId()).orElseThrow(() -> new NoSuchElementException("cart not found"));
        assertEquals(2, cart.getItems().get(0).getQuantity().intValue());
        // two items of 10 will give a total price  = 20
        assertEquals(20, cart.getTotalPrice().intValue());
    }

    @Test
    @Transactional
    public void testRemoveAllProductsFromCartWillUpdateTheTotalPriceToZero() throws InterruptedException {

        ShoppingCartDTO cart = cartService.initShoppingCart(1);
        cartService.addProductToCart("prod02", cart.getId(), new BigDecimal(3));
        cart = cartService.getShoppingCart(cart.getId()).orElseThrow(() -> new NoSuchElementException("cart not found"));
        assertEquals(3, cart.getItems().get(0).getQuantity().intValue());
        // 3 items of 5 euros will give a total price  = 15
        assertEquals(15, cart.getTotalPrice().intValue());
        // add the prod01 = 10euro
        cart = cartService.addProductToCart("prod01", cart.getId(), new BigDecimal(1));
        assertEquals(25, cart.getTotalPrice().intValue());

        // add the prod01 = 10euro
        cart = cartService.addProductToCart("prod01", cart.getId(), new BigDecimal(1));
        assertEquals(35, cart.getTotalPrice().intValue());

        cart = cartService.removeProductFromCart("prod02", cart.getId());
        assertEquals(20, cart.getTotalPrice().intValue());
        cart = cartService.removeProductFromCart("prod01", cart.getId());
        // no more items in the cart
        assertEquals(0, cart.getTotalPrice().intValue());
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    @Transactional
    public void addWrongProductReferenceThrowIllegalArgumentException() throws InterruptedException {
        ShoppingCartDTO cart = cartService.initShoppingCart(1);
        assertThrows(IllegalArgumentException.class, () -> cartService.addProductToCart("aWrongProduct", cart.getId(), BigDecimal.ONE));
    }

    @Test
    @Transactional
    public void getNoExistingCart() {

        assertEquals(cartService.getShoppingCart(UUID.randomUUID().toString()), Optional.empty());
    }

}