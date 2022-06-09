package ru.kazakova.snacky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kazakova.snacky.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
