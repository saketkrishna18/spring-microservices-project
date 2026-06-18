package com.microservices.userservice.service;

import com.microservices.userservice.dto.LoginRequest;
import com.microservices.userservice.dto.RegisterRequest;
import com.microservices.userservice.entity.User;
import com.microservices.userservice.repository.UserRepository;
import com.microservices.userservice.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository repository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder =
                passwordEncoder;
    }

    public String register(
            RegisterRequest request) {

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()))
                .role("USER")
                .build();

        repository.save(user);

        return "User Registered Successfully";
    }

    public String login(
            LoginRequest request) {

        User user = repository
                .findByEmail(
                        request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword());

        if (!matches) {

            throw new RuntimeException(
                    "Invalid password");
        }

        return jwtService.generateToken(
                user.getEmail(),
                user.getRole());
    }
}