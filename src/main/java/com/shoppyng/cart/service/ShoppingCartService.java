package com.shoppyng.cart.service;

import com.shoppyng.cart.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ShoppingCartService {

    /**
     * Simple storage of carts
     */
    Map<String, ShoppingCart> carts = new HashMap<String, ShoppingCart>();


    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    /**
     * Init a shopping cart when the customer visit the shop
     * If there is persisted carts, in a real use case, we can have a recycling strategy for basket
     * In more complex cases, the user was not logged in and added items to anonymous cart and after logging we find aout that he has an active cart
     *
     * @param customerid
     * @return
     */
    public ShoppingCart initShoppingCart(Integer customerid) {
        ShoppingCart cart = new ShoppingCart();
        cart.setId(UUID.randomUUID());
        Customer customer = customerService.getCustomer(customerid).orElseThrow(() -> new IllegalArgumentException("Unknown customer " + customerid));
        cart.setCustomer(customer);
        cart.setCreationDate(new Date());
        carts.put(cart.getId(), cart);
        return cart;
    }

    public ShoppingCart addProductToCart(String productId, String cartId, BigDecimal quantity) {

        Product product = productService.getProduct(productId).orElseThrow(() -> new IllegalArgumentException("Unknown product " + productId));
        ShoppingCart cart = getShoppingCart(cartId).orElseThrow(() -> new NoSuchElementException("cart not found"));
        Optional<CartItem> existing = cart.getItems().stream().filter(it -> it.getCode().equals(product.getReference())).findFirst();
        if (existing.isPresent()) {
            //  and item aleady exist, just add the desired quantity
            existing.get().setQuantity(existing.get().getQuantity().add(quantity));
            cartContentUpdated(cart);
            return cart;
        }
        // else create new item in the shopping cart
        CartItem item = new CartItem();
        item.setQuantity(quantity);
        item.setCode(product.getReference());
        item.setName(product.getName());
        item.setUnitPrice(product.getPrice());
        item.setDescription(product.getDescription());
        cart.addItem(item);
        // notify the cart update to racalculate prices et trgger other needed operations
        cartContentUpdated(cart);
        return cart;
    }


    private void checkProductAvailability(String productId, Double quantity) {
        // check if the product is available with the asked quantity
    }

    public ShoppingCart removeProductFromCart(String productId, String cartId) {
        ShoppingCart cart = getShoppingCart(cartId).orElseThrow(() -> new NoSuchElementException("cart dosent exist"));
        cart.removeItem(productId);
        cartContentUpdated(cart);
        return cart;
    }

    public Optional<ShoppingCart> getShoppingCart(String cartId) {
        return Optional.ofNullable(carts.get(cartId));
    }

    /**
     * Notify the art content modification to trigger any listening action
     * @param cart
     */
    private void cartContentUpdated(ShoppingCart cart){

        recomputeCartPrices(cart);
    }

    private void recomputeCartPrices(ShoppingCart cart) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem item :  cart.getItems()) {
            item.setCalculatedPrice(item.getUnitPrice().multiply(item.getQuantity()));
            totalPrice = totalPrice.add(item.getCalculatedPrice());
        }
        // we can add other costs related to the delivery ...
        cart.setTotalPrice(totalPrice);
    }
}
