package com.shopping.cart.api.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddressDTOTest {

    @Test
    public void testAddressDTO(){

        AddressDTO addressDTO = AddressDTO.builder()
                .id(1L)
                .state("state")
                .city("city")
                .zipCode("06000")
                .street("street")
                .build();

        System.out.println(addressDTO.toString());
        assertEquals(addressDTO.getId(),1);
        assertEquals(addressDTO.getState(),"state");
        assertEquals(addressDTO.getCity(),"city");
        assertEquals(addressDTO.getStreet(),"street");
        assertEquals(addressDTO.getZipCode(),"06000");
        assertEquals(addressDTO.toString(),"AddressDTO(id=1, street=street, city=city, state=state, zipCode=06000)");

        AddressDTO addressDTO1 = new AddressDTO(0L,"","","","");
        addressDTO1.setId(1L);
        addressDTO1.setStreet("street");
        addressDTO1.setCity("city");
        addressDTO1.setState("state");
        addressDTO1.setZipCode("06000");

        assertEquals(addressDTO,addressDTO1);
        assertEquals(addressDTO.hashCode(),addressDTO1.hashCode());

        assertEquals(AddressDTO.builder()
                .id(1L)
                .state("state")
                .city("city")
                .zipCode("06000")
                .street("street")
                .toString(),"AddressDTO.AddressDTOBuilder(id=1, street=street, city=city, state=state, zipCode=06000)");

    }

}