package com.example.tutorial.database;

import com.example.tutorial.models.Product;
import com.example.tutorial.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    // generate logger
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    // this Bean will auto trigger whenever the project is started
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                // generate sample data to db
//                Product product1 = new Product("p1", 2012, 2.11, "url1");
//                Product product2 = new Product( "p2", 2013, 3.11, "url2");
//                // print logger
//                logger.info("Insert data: " + productRepository.save(product1));
//                logger.info("Insert data: " + productRepository.save(product2));
            }
        };
    }
}
