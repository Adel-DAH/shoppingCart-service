package com.shoppyng.cart.api;

import com.shoppyng.cart.model.ShoppingCart;
import com.shoppyng.cart.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.NoSuchElementException;


@RestController
public class SimpleController {


    @Autowired
    private ShoppingCartService cartService;


    @Operation(summary = "Initialize a shopping cart"
            , description = "Create a new shopping cart for a customer"
            , tags = { "cart" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful cart creation",
                    content = @Content( schema = @Schema(implementation = ShoppingCart.class))),
            @ApiResponse(responseCode = "400", description = "Bad format or unknown customer",
                    content = @Content( schema = @Schema(implementation = ShoppingCart.class)))
    })
    @PostMapping(value = "/customer/{customerId}/cart")
    public ShoppingCart initCart(@PathVariable Integer customerId) {
        return cartService.initShoppingCart(customerId);
    }

    @Operation(summary = "Add product to cart"
            , description = "Add a product to the shopping cart with a desired quantity"
            , tags = { "cartItem" })
    @PostMapping(value = "/cart/{cartId}/item")
    //POST : http://localhost:8080/cart/1235756/item?productReference="HGDNGDT"&quantity=10.05
    public ShoppingCart addProductToCart(@PathVariable("cartId") String cartId, @RequestParam("productReference") String productReference, @RequestParam("quantity") BigDecimal quantity) {
        return cartService.addProductToCart(productReference, cartId, quantity);
    }

    @Operation(summary = "Remove product form  cart"
            , description = "Remove a product from the shopping cart"
            , tags = { "cartItem" })
    @DeleteMapping(value = "/cart/{cartId}/item/{reference}")
    public ShoppingCart removeProductFromCart(@PathVariable("cartId") String cartId, @PathVariable("reference") String productReference) {
        return cartService.removeProductFromCart(productReference, cartId);
    }

    @GetMapping(value = "/cart/{cartId}")
    public ShoppingCart getCart(@PathVariable("cartId") String cartId) {
        return cartService.getShoppingCart(cartId).orElseThrow(() -> new NoSuchElementException("cart not found"));
    }

}