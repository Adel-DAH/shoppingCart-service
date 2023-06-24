package com.shopping.cart.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartItemDTOTest {


    @Test
    void testCartItemDTO() {

        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(1)
                .code("code")
                .name("name")
                .description("description")
                .shoppingCartId("UUID")
                .unitPrice(BigDecimal.TEN)
                .quantity(BigDecimal.ONE)
                .calculatedPrice(BigDecimal.TEN)
                .build();

        CartItemDTO cartItemDTO2 = new CartItemDTO(1, "code", "name", "description", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.TEN, "UUID");

        assertEquals(cartItemDTO1, cartItemDTO2);
        assertEquals(cartItemDTO1.hashCode(), cartItemDTO2.hashCode());
        assertTrue(cartItemDTO1.canEqual(cartItemDTO2));
        assertEquals(cartItemDTO1.toString(),"CartItemDTO(id=1, code=code, name=name, description=description, unitPrice=10, quantity=1, calculatedPrice=10, shoppingCartId=UUID)");

        cartItemDTO2.setId(2);
        cartItemDTO2.setName("name2");
        cartItemDTO2.setCode("code2");
        cartItemDTO2.setShoppingCartId("UUID2");
        cartItemDTO2.setDescription("description2");
        cartItemDTO2.setUnitPrice(BigDecimal.ZERO);
        cartItemDTO2.setQuantity(BigDecimal.ZERO);
        cartItemDTO2.setCalculatedPrice(BigDecimal.ZERO);

        assertEquals(cartItemDTO2.getId(),2);

        assertEquals(cartItemDTO2.getName(),"name2");
        assertEquals(cartItemDTO2.getCode(),"code2");
        assertEquals(cartItemDTO2.getShoppingCartId(),"UUID2");
        assertEquals(cartItemDTO2.getDescription(),"description2");
        assertEquals(cartItemDTO2.getUnitPrice(),BigDecimal.ZERO);
        assertEquals(cartItemDTO2.getQuantity(),BigDecimal.ZERO);
        assertEquals(cartItemDTO2.getCalculatedPrice(),BigDecimal.ZERO);

        assertNotEquals(cartItemDTO1, cartItemDTO2);

    }

}