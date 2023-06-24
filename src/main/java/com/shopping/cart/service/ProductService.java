package com.shopping.cart.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shopping.cart.model.Product;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductService {


    private WebClient webClient;

    public ProductService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * For test purpose
     */

    public Optional<Product> createProduct(String name, String ref, String desc, BigDecimal price) throws JsonProcessingException {

        Product productToSave = new Product();
        productToSave.setName(name);
        productToSave.setReference(ref);
        productToSave.setDescription(desc);
        productToSave.setPrice(price);

        Product product = webClient.post()
                .uri( "/products")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(productToSave),Product.class)
                .retrieve().bodyToMono(Product.class)
                .onErrorResume(error -> Mono.empty())
                .block();
        return Optional.ofNullable(product);
    }

    public Optional<Product> getProduct(String productReference) {

        Product product = webClient.get()
                .uri( "/products/" + productReference)
                .retrieve().bodyToMono(Product.class)
                .onErrorResume(error -> Mono.empty())
                .block();
            return Optional.ofNullable(product);
    }

}
