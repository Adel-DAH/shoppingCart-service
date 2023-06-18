package com.shopping.cart.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {

    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;

}
