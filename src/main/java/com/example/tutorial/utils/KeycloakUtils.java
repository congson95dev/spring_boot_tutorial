package com.example.tutorial.utils;

import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

public class KeycloakUtils {
    private final Environment env;

    public KeycloakUtils(Environment env) {
        this.env = env;
    }

    public Keycloak getKeycloakInstance() {
        return Keycloak.getInstance(
                env.getProperty("keycloak.auth-server-url"),
                env.getProperty("keycloak.realm"),
                env.getProperty("keycloak-config.username"),
                env.getProperty("keycloak-config.password"),
                env.getProperty("keycloak-client.client-id"),
                env.getProperty("keycloak-client.client-secret"));
    }
}
