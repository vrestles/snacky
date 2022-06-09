package ru.kazakova.snacky.controller;

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
public class ClientController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProductsByBrand(@RequestParam String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        if (products == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
