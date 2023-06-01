package com.shoppyng.cart.service;

import com.shoppyng.cart.model.Customer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class CustomerService {


    private final Map<Integer, Customer> customers = new HashMap<Integer, Customer>();


    /**
     * For test purpose
     */
    @PostConstruct
    public void init() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setId(1);
        this.customers.put(customer.getId(), customer);
    }

    public Optional<Customer> getCustomer(Integer customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }

}
