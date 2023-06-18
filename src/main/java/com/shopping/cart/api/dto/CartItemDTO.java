package com.shopping.cart.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartItemDTO {

    private Integer id;
    private String code;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private BigDecimal quantity;
    private BigDecimal calculatedPrice;
    private String shoppingCartId;
}
