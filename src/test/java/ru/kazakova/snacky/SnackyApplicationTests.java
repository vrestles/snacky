package ru.kazakova.snacky;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kazakova.snacky.model.business.Product;
import ru.kazakova.snacky.model.security.Role;
import ru.kazakova.snacky.model.security.User;
import ru.kazakova.snacky.repository.ProductRepository;
import ru.kazakova.snacky.repository.RoleRepository;
import ru.kazakova.snacky.repository.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SnackyApplicationTests {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    private final String MANAGER_URL = "/api/v1/manager";
    private final String CLIENT_URL = "/api/v1/client";

    private final String ADMIN_TEST_LOGIN = "admin_test";
    private final String ADMIN_TEST_PASSWORD = "1111";
    private final String USER_TEST_LOGIN = "user_test";
    private final String USER_TEST_PASSWORD = "2222";
    private final String MANAGER_TEST_LOGIN = "manager_test";
    private final String MANAGER_TEST_PASSWORD = "3333";

    @BeforeEach
    public void cleanUp() {
        productRepository.deleteAll();
        if (userRepository.findByLogin(USER_TEST_LOGIN) == null) {
            registerUser();
        }
        if (userRepository.findByLogin(ADMIN_TEST_LOGIN) == null) {
            Role adminRole = roleRepository.findByName("ADMIN");
            createUserWithRole(ADMIN_TEST_LOGIN, ADMIN_TEST_PASSWORD, adminRole);
        }
    }

    @Test
    public void migrationSuccessTest() {
        // migration starts automatically,
        // since Spring Boot runs the Flyway scripts on startup
    }

    @Test
    public void encodePasswordTest() {
        String encodedString = passwordEncoder.encode("password");
        System.out.println("ENCODED_STRING=" + encodedString);
    }

    @Test
    public void managerPostProductSuccessTest() {
        Product product = new Product("Snaq Fabriq", "Bar", "Coco", "Coconut", 407.0, 40.0);

        HttpEntity<Product> httpEntity = new HttpEntity<>(product);
        ResponseEntity<Product> response = testRestTemplate
                .withBasicAuth(ADMIN_TEST_LOGIN, ADMIN_TEST_PASSWORD)
                .postForEntity(MANAGER_URL, httpEntity, Product.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void managerPostProductForUserFailTest() {
        Product product = new Product("Snaq Fabriq", "Bar", "Coco", "Coconut", 407.0, 40.0);

        HttpEntity<Product> httpEntity = new HttpEntity<>(product);
        ResponseEntity<Product> response = testRestTemplate
                .withBasicAuth(USER_TEST_LOGIN, USER_TEST_PASSWORD)
                .postForEntity(MANAGER_URL, httpEntity, Product.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void clientGetProductsByBrandSuccessTest() {
        // all products were in repository before calling endpoint
        String targetBrand = "Snaq Fabriq";
        Product productOne = new Product(targetBrand, "Bar", "Coco", "Coconut", 407.0, 40.0);
        Product productTwo = new Product(targetBrand, "Bar", "Coco", "Chocolate", 407.0, 40.0);
        Product productTree = new Product("Chicalab", "Chocolate","Protein Milk Chocolate", null, 324.0, 120.0);
        productRepository.saveAll(Arrays.asList(productOne, productTwo, productTree));



        ResponseEntity<Product[]> response = testRestTemplate
                .withBasicAuth(ADMIN_TEST_LOGIN, ADMIN_TEST_PASSWORD)
                .getForEntity(CLIENT_URL + "?brand=" + targetBrand, Product[].class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().length, 2);
    }

    @Test
    public void clientGetProductsByBrandForNotAuthFailTest() {
        String targetBrand = "Chicalab";
        Product product = new Product(targetBrand, "Chocolate","Protein Milk Chocolate", null, 324.0, 120.0);
        productRepository.save(product);

        ResponseEntity<Product> response = testRestTemplate
                .withBasicAuth("randomGuy", "5555")
                .getForEntity(CLIENT_URL + "?brand=" + targetBrand, Product.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void clientGetProductsByBrandNotFoundTest() {
        // there are no products in repository at all
        ResponseEntity<Product[]> response = testRestTemplate
                .withBasicAuth(ADMIN_TEST_LOGIN, ADMIN_TEST_PASSWORD)
                .getForEntity(CLIENT_URL + "?brand=" + "Chicalab", Product[].class);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    private void registerUser() {
        User admin = new User(USER_TEST_LOGIN, USER_TEST_PASSWORD);
        HttpEntity<User> httpEntity = new HttpEntity<>(admin);
        testRestTemplate.postForEntity("/registration", httpEntity, User.class);
    }

    private void createUserWithRole(String login, String password, Role role) {
        User user = new User(login, passwordEncoder.encode(password), role);
        userRepository.save(user);
    }
}
