package com.shopping.cart.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@Builder
public class ShoppingCartDTO {


    private String id;
    private Date creationDate;
    private CustomerDTO customer;
    private List<CartItemDTO> items;
    private BigDecimal totalPrice;
    private BigDecimal totalPriceWithVAT;

}
