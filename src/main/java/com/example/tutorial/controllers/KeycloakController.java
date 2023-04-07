package com.example.tutorial.controllers;

import com.example.tutorial.models.KeyCloakUser;
import com.example.tutorial.models.Product;
import com.example.tutorial.models.ResponseObject;
import com.example.tutorial.utils.KeycloakUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.credential.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.tutorial.common.Common.STATUS_FAILED;
import static com.example.tutorial.common.Common.STATUS_OK;

@RestController
@RequestMapping(path = "/api/v1/keycloak")
public class KeycloakController {
    @Autowired
    Environment env;

//    This is sample of using keycloak to get all users or get users by params "username"
//    Here's how to use:
//    /users?username=admin&page=0&size=2

    @GetMapping("/users")
    ResponseEntity<ResponseObject> getAllUsers(
            @RequestParam(required=false) String username,
            Pageable pageable
    ) {
        KeycloakUtils keycloakUtils = new KeycloakUtils(env);
        Keycloak keycloak = keycloakUtils.getKeycloakInstance();
        try {
            UsersResource usersResource = keycloak.realm(env.getProperty("keycloak.realm")).users();
            List<UserRepresentation> userRepresentations;
            List<UserRepresentation> searchResults;

            // handle params username
            if (username != null) {
                searchResults = usersResource.search(username);
                userRepresentations = searchResults.subList((int) pageable.getOffset(), Math.min((int) pageable.getOffset() + pageable.getPageSize(), searchResults.size()));
            } else {
                userRepresentations = usersResource.list((int) pageable.getOffset(), pageable.getPageSize());
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(STATUS_OK, "Success", userRepresentations));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (keycloak != null) {
                keycloak.close();
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(STATUS_FAILED, "Something went wrong", "")
        );
    }

    @GetMapping("/users/{id}")
    ResponseEntity<ResponseObject> getUserById(
            @PathVariable String id
    ) {
        KeycloakUtils keycloakUtils = new KeycloakUtils(env);
        Keycloak keycloak = keycloakUtils.getKeycloakInstance();
        try {
            UsersResource usersResource = keycloak.realm(env.getProperty("keycloak.realm")).users();
            UserRepresentation searchResult = usersResource.get(id).toRepresentation();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(STATUS_OK, "Success", searchResult));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (keycloak != null) {
                keycloak.close();
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(STATUS_FAILED, "Something went wrong", "")
        );
    }

    @PostMapping("/users")
    ResponseEntity<ResponseObject> createUser(@RequestBody KeyCloakUser keyCloakUser) {
        KeycloakUtils keycloakUtils = new KeycloakUtils(env);
        Keycloak keycloak = keycloakUtils.getKeycloakInstance();
        try {
            UsersResource usersResource = keycloak.realm(env.getProperty("keycloak.realm")).users();

            // create password
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(keyCloakUser.getPassword());
            credential.setTemporary(false);

            // set info to user
            UserRepresentation user = new UserRepresentation();
            user.setUsername(keyCloakUser.getUserName());
            user.setFirstName(keyCloakUser.getFirstname());
            user.setLastName(keyCloakUser.getLastName());
            user.setEmail(keyCloakUser.getEmail());
            user.setCredentials(Collections.singletonList(credential));
            user.setAttributes(Collections.singletonMap("phone", Collections.singletonList(keyCloakUser.getPhoneNumber())));
            user.setEnabled(true);

            // create user
            Response response = usersResource.create(user);

            if (response.getStatus() == HttpStatus.CREATED.value()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(STATUS_OK, "Success", user));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        new ResponseObject(STATUS_FAILED, "Failed to create user", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(STATUS_FAILED, "Something went wrong", e.getMessage())
            );
        } finally {
            if (keycloak != null) {
                keycloak.close();
            }
        }
    }
}
