package com.shopping.cart.service;

import com.shopping.cart.api.dto.ShoppingCartDTO;
import com.shopping.cart.api.mapper.ShoppingCartMapper;
import com.shopping.cart.model.*;
import com.shopping.cart.repository.ShoppingCartRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ShoppingCartService {

    /**
     * Simple storage of carts
     */
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    private CartItemService itemService;

    public ShoppingCartService(ShoppingCartMapper shoppingCartMapper, ShoppingCartRepository shoppingCartRepository, CustomerService customerService, ProductService productService, CartItemService itemService) {

        this.shoppingCartMapper = shoppingCartMapper;
        this.shoppingCartRepository = shoppingCartRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.itemService = itemService;
    }

    /**
     * Init a shopping cart when the customer visit the shop
     * If there is persisted carts, in a real use case, we can have a recycling strategy for basket
     * In more complex cases, the user was not logged in and added items to anonymous cart and after logging we find aout that he has an active cart
     *
     * @param customerId
     * @return ShoppingCartDTO
     */
    public ShoppingCartDTO initShoppingCart(Integer customerId) {

        Customer customer = customerService.getCustomer(customerId).orElseThrow(() -> new IllegalArgumentException("Unknown customer " + customerId));

        shoppingCartRepository.findByCustomerId(customerId).ifPresent(shoppingCartRepository::delete);

        ShoppingCart cart = new ShoppingCart();
        cart.setId(UUID.randomUUID());
        cart.setCustomer(customer);
        cart.setCreationDate(new Date());
        customer.setShoppingCart(cart);
        shoppingCartRepository.save(cart);
        return shoppingCartMapper.toDTO(cart);
    }

    public ShoppingCartDTO addProductToCart(String productReference, String cartId, BigDecimal quantity) {

        Product product = productService.getProduct(productReference).orElseThrow(() -> new IllegalArgumentException("Unknown product " + productReference));
        ShoppingCart cart = shoppingCartRepository.findById(UUID.fromString(cartId)).orElseThrow(() -> new NoSuchElementException("cart not found"));
        Optional<CartItem> existing = cart.getItems().stream().filter(it -> it.getCode().equals(product.getReference())).findFirst();
        if (existing.isPresent()) {
            //  and item aleady exist, just add the desired quantity
            existing.get().setQuantity(existing.get().getQuantity().add(quantity));
            cartContentUpdated(cart);
        } else {
            // else create new item in the shopping cart
            CartItem item = new CartItem();
            item.setQuantity(quantity);
            item.setCode(product.getReference());
            item.setName(product.getName());
            item.setUnitPrice(product.getPrice());
            item.setDescription(product.getDescription());
            item.setShoppingCart(cart);
            cart.addItem(itemService.save(item));
            // notify the cart update to racalculate prices et trgger other needed operations
            cartContentUpdated(cart);
        }
        return shoppingCartMapper.toDTO(shoppingCartRepository.save(cart));
    }


    private void checkProductAvailability(String productId, Double quantity) {
        // check if the product is available with the asked quantity
    }

    public ShoppingCartDTO removeProductFromCart(String productId, String cartId) {

        ShoppingCart cart = shoppingCartRepository.findById(UUID.fromString(cartId)).orElseThrow(() -> new NoSuchElementException("cart dosent exist"));

        cart.removeItem(productId);
        cartContentUpdated(cart);

        return shoppingCartMapper.toDTO(cart);
    }

    public Optional<ShoppingCartDTO> getShoppingCart(String cartId) {
        return shoppingCartRepository.findById(UUID.fromString(cartId))
                .map(shoppingCartMapper::toDTO);
    }

    /**
     * Notify the art content modification to trigger any listening action
     *
     * @param cart
     */
    private void cartContentUpdated(ShoppingCart cart) {

        recomputeCartPrices(cart);
    }

    private void recomputeCartPrices(ShoppingCart cart) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            item.setCalculatedPrice(item.getUnitPrice().multiply(item.getQuantity()));
            totalPrice = totalPrice.add(item.getCalculatedPrice());
        }
        // we can add other costs related to the delivery ...
        cart.setTotalPrice(totalPrice);
    }
}
