package ru.kazakova.snacky.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kazakova.snacky.model.Product;
import ru.kazakova.snacky.service.ProductService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Controller for admin requests")
public class AdminController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Add new product to the database")
    @ApiResponse(responseCode = "201", description = "Product successfully added to the database")
    public ResponseEntity<Product> save(@RequestBody Product product) {
        Product savedProduct = productService.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
}
