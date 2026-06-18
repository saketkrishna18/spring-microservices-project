package com.microservices.orderservice.external.client;

import com.microservices.orderservice.dto.UserResponse;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "USER-SERVICE"
)
public interface UserClient {

    @GetMapping("/auth/user")
    UserResponse
    getUserByEmail(

            @RequestParam
            String email
    );
}