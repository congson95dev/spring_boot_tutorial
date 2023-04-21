package com.example.tutorial.services.impl;

import com.example.tutorial.models.Product;
import com.example.tutorial.models.ResponseObject;
import com.example.tutorial.repositories.ProductRepository;
import com.example.tutorial.services.ProductService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

import static com.example.tutorial.common.Common.STATUS_FAILED;
import static com.example.tutorial.common.Common.STATUS_OK;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository repository;
    public ResponseEntity<ResponseObject> getAllProducts() {
        List<Product> products = repository.findAll();
        return (!products.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject<List<Product>>(STATUS_OK, "Success", products)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(STATUS_FAILED, "Don't have any products ", "")
                );
    }

    public ResponseEntity<ResponseObject> getProductDetail(@PathVariable String id) {
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

    public ResponseEntity<ResponseObject> insertProduct(@RequestBody Product product) {
        // check if product name is already exist in db
        boolean checkIfProductNameExist = this.checkIfProductNameExist(product);
        if (checkIfProductNameExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(STATUS_FAILED, "This product name is already taken", "")
            );
        }
        // insert to db
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject(STATUS_OK, "Created Successfully", repository.insert(product))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@RequestBody Product product, @PathVariable String id) {
        // check if product name is already exist in db
        boolean checkIfProductNameExist = this.checkIfProductNameExist(product);
        if (checkIfProductNameExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(STATUS_FAILED, "This product name is already taken", "")
            );
        }

        // check if exists
        Product updatedProduct = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Can't find product with id %s", id)
                ));

        // update data
        updatedProduct.setProductName(product.getProductName());
        updatedProduct.setProductYear(product.getProductYear());
        updatedProduct.setProductPrice(product.getProductPrice());
        updatedProduct.setProductUrl(product.getProductUrl());

        // save to db
        repository.save(updatedProduct);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(STATUS_OK, "Updated Successfully", updatedProduct)
        );
    }

    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable String id) {
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
