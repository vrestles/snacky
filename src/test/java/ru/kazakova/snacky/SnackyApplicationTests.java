package ru.kazakova.snacky;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.kazakova.snacky.model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SnackyApplicationTests {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    private final String ADMIN_URL = "/api/v1/admin";

    @Test
    public void migrationSuccessTest() {
        // migration starts automatically,
        // since Spring Boot runs the Flyway scripts on startup
    }

    @Test
    public void adminPostProductSuccessTest() {
        Product product = Product.builder()
                .brand("Snaq Fabriq")
                .category("Bar")
                .name("Coco")
                .flavour("Coconut")
                .caloriesPer100Gram(407.0)
                .weight(40.0)
                .build();

        HttpEntity<Product> httpEntity = new HttpEntity<>(product);
        ResponseEntity<Product> response = testRestTemplate.exchange(
                ADMIN_URL, HttpMethod.POST, httpEntity, Product.class);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }
}
