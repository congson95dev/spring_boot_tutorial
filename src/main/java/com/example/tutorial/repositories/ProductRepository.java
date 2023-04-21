package com.example.tutorial.repositories;

import com.example.tutorial.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// When we extends from MongoRepository, we'll have every function it have, such as paging, crud ..

// @Repository will lets spring boot know that this class is repository
@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {
    @Query("{'name': ?0}")
    List<Product> findByProductName(String productName);

    // in this mongodb, we need to override the findById, existsById, deleteById so it use String for "id"
    // or else, it will force you to use Long for "id"
    Optional<Product> findById(String id);
    boolean existsById(String id);
    void deleteById(String id);
}
