package com.example.tutorial.changelogs;


import com.example.tutorial.models.Product;
import com.example.tutorial.repositories.ProductRepository;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangeLog {

    // it will run by order
    // ex: 001, 002, 003 ...
    @ChangeSet(order="001", id="seedDatabase", author="fudo")
    public void seedDatabase(ProductRepository productRepository) {
        // create 3 sample product using mongock
        List<Product> products = new ArrayList<>();
        products.add(createNewProduct("p1", 2000, 2.11, ""));
        products.add(createNewProduct("p2", 2000, 2.11, ""));
        products.add(createNewProduct("p3", 2000, 2.11, ""));

        productRepository.insert(products);
    }

    public Product createNewProduct(String productName, int productYear, Double productPrice, String productUrl) {
        Product product = new Product();
        product.setProductName(productName);
        product.setProductYear(productYear);
        product.setProductPrice(productPrice);
        product.setProductUrl(productUrl);
        return product;
    }
}
