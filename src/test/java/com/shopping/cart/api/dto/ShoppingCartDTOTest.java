package com.shopping.cart.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartDTOTest {


    @Test
    void testShoppingCartDTO() {

        CustomerDTO customerDTO = new CustomerDTO(1,"jonatan","joestar",1L,"UUID");

        ShoppingCartDTO cartDTO1 = ShoppingCartDTO.builder()
                .id("UUID")
                .creationDate(new Date())
                .customer(customerDTO)
                .items(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .totalPriceWithVAT(BigDecimal.ZERO)
                .build();

        ShoppingCartDTO cartDTO2 = new ShoppingCartDTO("UUID",new Date(), customerDTO,new ArrayList<>(),BigDecimal.ZERO,BigDecimal.ZERO);

        assertEquals(cartDTO1, cartDTO2);
        assertEquals(cartDTO1.hashCode(), cartDTO2.hashCode());
        assertTrue(cartDTO1.canEqual(cartDTO2));
        assertEquals(cartDTO1.toString(),"ShoppingCartDTO(id=UUID, creationDate="+cartDTO1.getCreationDate()+", customer=CustomerDTO(id=1, firstName=jonatan, lastName=joestar, addressId=1, shoppingCartId=UUID), items=[], totalPrice=0, totalPriceWithVAT=0)");

        cartDTO2.setId("UUID2");
        cartDTO2.setCustomer(customerDTO);
        cartDTO2.setItems(new ArrayList<>());
        cartDTO2.setCreationDate(new Date());
        cartDTO2.setTotalPrice(BigDecimal.TEN);
        cartDTO2.setTotalPriceWithVAT(BigDecimal.TEN);

        assertEquals(cartDTO2.getId(), "UUID2");
        assertEquals(cartDTO2.getCustomer(), customerDTO);
        assertEquals(cartDTO2.getItems(), new ArrayList<>());
        assertEquals(cartDTO2.getCreationDate(), new Date());
        assertEquals(cartDTO2.getTotalPrice(), BigDecimal.TEN);
        assertEquals(cartDTO2.getTotalPriceWithVAT(), BigDecimal.TEN);

        assertNotEquals(cartDTO1, cartDTO2);
    }

}