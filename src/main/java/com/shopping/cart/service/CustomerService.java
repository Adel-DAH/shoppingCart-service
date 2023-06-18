package com.shopping.cart.service;

import com.shopping.cart.model.Address;
import com.shopping.cart.model.Customer;

import com.shopping.cart.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressService addressService;

    public CustomerService(CustomerRepository customerRepository, AddressService addressService) {
        this.customerRepository = customerRepository;

        this.addressService =addressService;
    }


    /**
     * For test purpose
     */
    @PostConstruct
    public void init() {

        if (customerRepository.findById(1).isEmpty()) {

            Customer customer = new Customer();
            customer.setFirstName("John");
            customer.setLastName("Doe");
            customer.setId(1);
            customerRepository.save(customer);

            Address address = new Address();
            address.setId(1L);
            address.setCity("City");
            address.setState("State");
            address.setStreet("Street");
            address.setZipCode("06000");
            address.setCustomer(customer);
            addressService.save(address);
        }
    }

    public Optional<Customer> getCustomer(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    public void save(Customer customer){

        customerRepository.save(customer);

    }

}
