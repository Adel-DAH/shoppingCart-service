package com.shopping.cart.service;

import com.shopping.cart.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {


    /**
     * For test purpose
     */

    private Product createProduct(String name, String ref, String desc, BigDecimal price) {
        Product prod = new Product();
        prod.setName(name);
        prod.setReference(ref);
        prod.setDescription(desc);
        prod.setPrice(price);
        return prod;
    }

    public Optional<Product> getProduct(String productReference) {

        WebClient client = WebClient.create();
        Product product = client.get()
                .uri( "http://localhost:8082/product/" + productReference)
                .retrieve().bodyToMono(Product.class)
                .onErrorResume(error -> Mono.empty())
                .block();
            return Optional.ofNullable(product);
    }

}
