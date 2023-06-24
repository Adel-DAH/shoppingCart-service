package com.shopping.cart.api.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDTOTest {

    @Test
    void testCustomerDTO() {
        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .addressId(100L)
                .shoppingCartId("UUID")
                .build();

        CustomerDTO customerDTO2 = new CustomerDTO(1, "John", "Doe", 100L, "UUID");

        assertEquals(customerDTO1, customerDTO2);
        assertEquals(customerDTO1.hashCode(), customerDTO2.hashCode());
        assertTrue(customerDTO1.canEqual(customerDTO2));
        assertEquals(customerDTO1.toString(), "CustomerDTO(id=1, firstName=John, lastName=Doe, addressId=100, shoppingCartId=UUID)");

        customerDTO2.setId(2);
        customerDTO2.setFirstName("Jane");
        customerDTO2.setLastName("Smith");
        customerDTO2.setAddressId(200L);
        customerDTO2.setShoppingCartId("UUID2");

        assertEquals(customerDTO2.getId(), 2);
        assertEquals(customerDTO2.getFirstName(), "Jane");
        assertEquals(customerDTO2.getLastName(), "Smith");
        assertEquals(customerDTO2.getAddressId(), 200L);
        assertEquals(customerDTO2.getShoppingCartId(), "UUID2");

        assertNotEquals(customerDTO1, customerDTO2);
    }

}