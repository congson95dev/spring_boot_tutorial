package com.example.tutorial.controllers;

import com.example.tutorial.models.Product;
import com.example.tutorial.models.ResponseObject;
import com.example.tutorial.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @GetMapping("")
    ResponseEntity<ResponseObject> getAllProducts() {
        return productService.getAllProducts();
    }

    // @PathVariable is to get the params
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getProductDetail(@PathVariable Long id) {
        return productService.getProductDetail(id);
    }

    // @RequestBody is to get the body
    // must choose Raw, Json in postman to make this work
    @PostMapping("")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product product) {
        return productService.insertProduct(product);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        return productService.updateProduct(newProduct, id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
