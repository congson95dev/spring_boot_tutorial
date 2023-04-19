package com.example.tutorial.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class JsonUtils {
    private final ObjectMapper objectMapper;

    public <T> String convertObjToString(T data) {
        try {
            return this.objectMapper.writeValueAsString(data);
        } catch (Exception ex) {
            return null;
        }
    }
}
