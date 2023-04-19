package com.example.tutorial.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// this controller used to test the error handling in src/main/java/com/example/tutorial/exception/CommonExceptionHandler.java
@RestController
@RequestMapping(path = "/api/v1/error-testing")
@AllArgsConstructor
public class ErrorController {
    @GetMapping("")
    public ResponseEntity<String> errorTestingMethod() {
        // throw new http error 500
        throw new RuntimeException("Test exception");
    }
}
