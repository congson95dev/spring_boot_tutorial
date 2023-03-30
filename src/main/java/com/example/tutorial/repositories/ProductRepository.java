package com.example.tutorial.repositories;

import com.example.tutorial.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // this function is automatic created by JPA, we just need to call it out in this interface and use it in the controller
    // syntax: findBy + field name
    // ex: findByProductName or findByProductYear
    List<Product> findByProductName(String productName);
}
