package com.microservices.userservice.controller;

import com.microservices.userservice.dto.LoginRequest;
import com.microservices.userservice.dto.RegisterRequest;
import com.microservices.userservice.entity.User;
import com.microservices.userservice.repository.UserRepository;
import com.microservices.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;
    private final UserRepository userRepository;
    public AuthController(
            UserService service,
            UserRepository userRepository) {

        this.service = service;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String register(
            @RequestBody
            RegisterRequest request) {

        return service.register(request);
    }

    @PostMapping("/login")
    public String login(
            @RequestBody
            LoginRequest request) {

        return service.login(request);
    }

    @GetMapping("/user")
    public User getUserByEmail(

            @RequestParam
            String email) {

        return userRepository
                .findByEmail(
                        email
                )
                .orElseThrow(
                        () ->
                                new RuntimeException(
                                        "User not found"
                                )
                );
    }
}