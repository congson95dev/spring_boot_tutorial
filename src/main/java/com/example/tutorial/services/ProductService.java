package com.example.tutorial.services;

import com.example.tutorial.models.Product;
import com.example.tutorial.models.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService {
    ResponseEntity<ResponseObject> getAllProducts();
    ResponseEntity<ResponseObject> getProductDetail(@PathVariable Long id);
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product product);
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id);
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id);
}
