package com.shopping.cart.model;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * In a real world, a customer have a lot of other information
 * The access credentials to the system, loyalty card, contact preferences, subscriptions for feeds ...
 */
@Entity
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    /**
     * This a simple contact implementation, is real usage we can meet multi contact management to let customers manage more than one address
     * We can also separate delivery address from billing address.
     */
    @OneToOne(mappedBy = "customer")
    private Address address;

    @OneToOne(mappedBy = "customer")
    private ShoppingCart shoppingCart;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address);
    }
}
