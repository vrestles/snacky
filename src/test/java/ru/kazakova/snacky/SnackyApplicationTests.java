package ru.kazakova.snacky;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.kazakova.snacky.model.Product;
import ru.kazakova.snacky.repository.ProductRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SnackyApplicationTests {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected ProductRepository productRepository;

    private final String ADMIN_URL = "/api/v1/admin";
    private final String CLIENT_URL = "/api/v1/client";

    @BeforeEach
    public void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    public void migrationSuccessTest() {
        // migration starts automatically,
        // since Spring Boot runs the Flyway scripts on startup
    }

    @Test
    public void adminPostProductSuccessTest() {
        Product product = getTestProduct("Snaq Fabriq", "Bar", "Coco", "Coconut", 407.0, 40.0);

        HttpEntity<Product> httpEntity = new HttpEntity<>(product);
        ResponseEntity<Product> response = testRestTemplate.exchange(
                ADMIN_URL, HttpMethod.POST, httpEntity, Product.class);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void clientGetProductsByBrandSuccessTest() {
        // all products were in repository before calling endpoint
        String targetBrand = "Snaq Fabriq";
        Product productOne = getTestProduct(targetBrand, "Bar", "Coco", "Coconut", 407.0, 40.0);
        Product productTwo = getTestProduct(targetBrand, "Bar", "Coco", "Chocolate", 407.0, 40.0);
        Product productTree = getTestProduct("Chicalab", "Chocolate","Protein Milk Chocolate", null, 324.0, 120.0);
        productRepository.saveAll(Arrays.asList(productOne, productTwo, productTree));

        ResponseEntity<Product[]> response = testRestTemplate.getForEntity(
                CLIENT_URL + "?brand=" + targetBrand, Product[].class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().length, 2);
    }

    @Test
    public void clientGetProductsByBrandNotFoundTest() {
        // there are no products in repository at all
        ResponseEntity<Product[]> response = testRestTemplate.getForEntity(
                CLIENT_URL + "?brand=" + "Chicalab", Product[].class);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    private Product getTestProduct(String brand, String category, String name,
                                   String flavour, Double caloriesPer100Gram, Double weight) {
        return Product.builder()
                .brand(brand)
                .category(category)
                .name(name)
                .flavour(flavour)
                .caloriesPer100Gram(caloriesPer100Gram)
                .weight(weight)
                .build();
    }
}
