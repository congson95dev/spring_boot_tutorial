package com.example.tutorial.config;

import com.example.tutorial.utils.KeycloakUtils;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;


@Configuration
public class KeyCloakConfiguration {
    @Autowired
    private Environment env;

    @Bean
    public KeycloakUtils KeycloakUtils() {
        return new KeycloakUtils(env);
    }

    @Bean
    public KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}
