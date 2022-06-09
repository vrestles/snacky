package ru.kazakova.snacky.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kazakova.snacky.model.Product;
import ru.kazakova.snacky.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product) {
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

}
