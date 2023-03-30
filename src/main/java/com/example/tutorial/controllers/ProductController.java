package com.example.tutorial.controllers;

import com.example.tutorial.models.Product;
import com.example.tutorial.models.ResponseObject;
import com.example.tutorial.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.tutorial.common.Common.STATUS_FAILED;
import static com.example.tutorial.common.Common.STATUS_OK;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {
    // Dependency Injection
    // not sure how this work yet
    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    List<Product> getAllProducts() {
        return repository.findAll();
    }

    // @PathVariable is to get the params
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getProductDetail(@PathVariable Long id) {
        // Optional is used because the response maybe null
        Optional<Product> product = repository.findById(id);
        return (product.isPresent()) ?
            ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(STATUS_OK, "Success", product)
            ):
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(STATUS_FAILED, "Can't not found product with id " + id, "")
            );
    }

    // check if product name is already exist in db
    public boolean checkIfProductNameExist(Product product) {
        List<Product> findByNameProducts = repository.findByProductName(product.getProductName().trim());
        return findByNameProducts.size() > 0;
    }

    // @RequestBody is to get the body
    // must choose Raw, Json in postman to make this work
    @PostMapping("")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product product) {
        // check if product name is already exist in db
        boolean checkIfProductNameExist = this.checkIfProductNameExist(product);
        if (checkIfProductNameExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(STATUS_FAILED, "This product name is already taken", "")
            );
        }
        // insert to db
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(STATUS_OK, "Created Successfully", repository.save(product))
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        // check if product name is already exist in db
        boolean checkIfProductNameExist = this.checkIfProductNameExist(newProduct);
        if (checkIfProductNameExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(STATUS_FAILED, "This product name is already taken", "")
            );
        }

        // find product
        // if exists, update product data, otherwise, create new product
        Product updatedProduct = repository.findById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setProductYear(newProduct.getProductYear());
                    product.setProductPrice(newProduct.getProductPrice());
                    product.setProductUrl(newProduct.getProductUrl());
                    return repository.save(product);
                }).orElseGet(() -> {
                    return repository.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(STATUS_OK, "Updated Successfully", updatedProduct)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        boolean productExist = repository.existsById(id);
        if (productExist) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(STATUS_OK, "Deleted Successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(STATUS_FAILED, "Can't not found product with id " + id, "")
        );
    }
}
