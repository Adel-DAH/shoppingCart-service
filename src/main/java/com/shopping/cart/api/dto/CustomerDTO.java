package com.shopping.cart.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private Long addressId;
    private String shoppingCartId;
}
