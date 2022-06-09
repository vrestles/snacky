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

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
@Tag(name = "Controller for client requests")
public class ClientController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get products by the name of the brand")
    @ApiResponse(responseCode = "200", description = "Product were found in the database")
    @ApiResponse(responseCode = "404", description = "No products were found")
    public ResponseEntity<List<Product>> getProductsByBrand(@RequestParam String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        if (products == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
