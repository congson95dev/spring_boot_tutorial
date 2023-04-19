package com.example.tutorial.repositories;

import com.example.tutorial.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// When we extends from JpaRepository, we'll have every function it have, such as paging, crud, flush ..

// @Repository will lets spring boot know that this class is repository
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // this function is automatic created by JPA, we just need to call it out in this interface and use it in the controller
    // syntax: findBy + field name
    // ex: findByProductName or findByProductYear
    List<Product> findByProductName(String productName);
}
