package com.shopping.cart.service;

import com.shopping.cart.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {


    private final Map<String, Product> products = new HashMap<String, Product>();

    /**
     * For test purpose
     */
    @PostConstruct
    public void init() {
        Product prod1 = createProduct("Milka", "prod01", "this is our Milka description", BigDecimal.TEN);
        Product prod2 = createProduct("Cheese", "prod02", "this is our Cheese description", new BigDecimal(5));
        this.products.put(prod1.getReference(), prod1);
        this.products.put(prod2.getReference(), prod2);
    }

    private Product createProduct(String name, String ref, String desc, BigDecimal price) {
        Product prod = new Product();
        prod.setName(name);
        prod.setReference(ref);
        prod.setDescription(desc);
        prod.setPrice(price);
        return prod;
    }

    public Optional<Product> getProduct(String productReference) {
        return Optional.ofNullable(products.get(productReference));
    }

}
