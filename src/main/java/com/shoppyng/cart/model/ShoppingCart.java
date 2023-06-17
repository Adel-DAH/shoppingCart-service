package com.shoppyng.cart.model;


import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "creationDate")
    private Date creationDate;

    @OneToOne
    private Customer customer;

    @OneToMany
    private List<CartItem> items = new ArrayList<>(0);

    private BigDecimal totalPrice;

    private BigDecimal totalPriceWithVAT;

    public String getId() {
        return id.toString();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPriceWithVAT() {
        return totalPriceWithVAT;
    }

    public void setTotalPriceWithVAT(BigDecimal totalPriceWithVAT) {
        this.totalPriceWithVAT = totalPriceWithVAT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(items, that.items) &&
                Objects.equals(totalPrice, that.totalPrice) &&
                Objects.equals(totalPriceWithVAT, that.totalPriceWithVAT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, customer, items, totalPrice, totalPriceWithVAT);
    }

    public void addItem(CartItem item) {
        this.items.add(item);
    }

    public void removeItem(String reference) {
        List<CartItem> existing = this.items.stream().filter(it -> it.getCode().equals(reference)).collect(Collectors.toList());
        // remove all items corresponding to the given product reference
        existing.forEach(it -> this.items.remove(it));
    }
}
