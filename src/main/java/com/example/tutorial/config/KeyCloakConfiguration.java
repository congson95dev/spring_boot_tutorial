package com.example.tutorial.config;

import com.example.tutorial.utils.KeycloakUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class KeyCloakConfiguration {
    @Autowired
    private Environment env;

    @Bean
    public KeycloakUtils KeycloakUtils() {
        return new KeycloakUtils(env);
    }
}
