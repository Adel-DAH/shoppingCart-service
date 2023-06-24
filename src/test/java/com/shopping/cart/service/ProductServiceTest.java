package com.shopping.cart.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.cart.model.Product;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.*;

import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private static ProductService productService;

    public static MockWebServer mockProductProvider;

    public WebClient webClient;

    @BeforeAll
     static void initMockWebServer() throws IOException {
        mockProductProvider = new MockWebServer();
        mockProductProvider.start();
    }

    @AfterAll
     static void tearDownMockWebServer() throws IOException {
        mockProductProvider.shutdown();
    }


    @BeforeEach
    void setUp() {

        webClient = WebClient.builder()
                .baseUrl("http://localhost:"+mockProductProvider.getPort())
                .build();
        productService = new ProductService(webClient);
    }


    @Test
    public void getProductWithInvalidProductProviderService() {
        webClient = WebClient.create();
        productService = new ProductService(webClient);
        assertEquals(productService.getProduct("REF"), Optional.empty());
    }
    @Test
    public void getNoExistingProduct() {

        mockProductProvider.enqueue(new MockResponse()
                .setResponseCode(500)
                .addHeader("Content-Type", "application/json"));

        Optional<Product> product  = productService.getProduct("ref");

        assertEquals(Optional.empty(), product);

    }

    @Test
    public void getProduct() throws IOException {

        Product expectedProduct = new Product();
        expectedProduct.setName("name");
        expectedProduct.setReference("ref");
        expectedProduct.setDescription("desc");
        expectedProduct.setPrice(BigDecimal.TEN);

        ObjectMapper objectMapper = new ObjectMapper();

        mockProductProvider.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(expectedProduct))
                .addHeader("Content-Type", "application/json"));

        Optional<Product> product  = productService.getProduct("ref");

        assertEquals(Optional.of(expectedProduct), product);

    }

    @Test
    public void createProductWithInvalidProductProviderService() throws JsonProcessingException {

        webClient = WebClient.create();
        productService = new ProductService(webClient);

        assertEquals(productService.createProduct("","","",BigDecimal.TEN), Optional.empty());
    }

    @Test
    public void createProductWithInvalidParameters() throws JsonProcessingException {

        mockProductProvider.enqueue(new MockResponse()
                .setResponseCode(500)
                .addHeader("Content-Type", "application/json"));

        Optional<Product> product  = productService.createProduct("","","",BigDecimal.TEN);

        assertEquals(Optional.empty(), product);

    }

    @Test
    public void createProduct() throws IOException, InterruptedException {

        Product expectedProduct = new Product();
        expectedProduct.setName("name");
        expectedProduct.setReference("ref");
        expectedProduct.setDescription("desc");
        expectedProduct.setPrice(BigDecimal.TEN);

        ObjectMapper objectMapper = new ObjectMapper();

        mockProductProvider.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(expectedProduct))
                .addHeader("Content-Type", "application/json"));

        Optional<Product> product  = productService.createProduct("name","ref","desc",BigDecimal.TEN);

        assertEquals(Optional.of(expectedProduct), product);

    }

}