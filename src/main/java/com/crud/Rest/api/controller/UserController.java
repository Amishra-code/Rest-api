package com.crud.Rest.api.controller;

import com.crud.Rest.api.model.Users;
import com.crud.Rest.api.service.MyUserDetailsService;
import com.crud.Rest.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    /** Registers a new user and returns the created user with a 201 status, or an error message if failed. */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {
        try {
            Users userCreated = service.register(user);
            return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** Verifies the user's login credentials and returns a JWT token if successful. */
    @PostMapping("/login")
    public String login(@RequestBody Users user) {

        return service.verify(user);
    }

}
