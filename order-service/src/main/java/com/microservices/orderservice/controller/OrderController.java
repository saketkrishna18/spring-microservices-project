package com.microservices.orderservice.controller;

import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.dto.OrderResponse;
import com.microservices.orderservice.security.JwtService;
import com.microservices.orderservice.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;
    private final JwtService jwtService;


    public OrderController(
            OrderService service,
            JwtService jwtService) {

        this.service = service;
        this.jwtService = jwtService;
    }

    @GetMapping
    public String test() {
        return "Order Service Working";
    }
    @PostMapping
    public OrderResponse createOrder(
            @RequestBody
            OrderRequest request,
            HttpServletRequest httpRequest) {

        String authHeader =
                httpRequest.getHeader(
                        "Authorization");

        String token =
                authHeader.substring(7);

        String email =
                jwtService.extractEmail(
                        token);
        System.out.println(
                "Order placed by user: " + email);
        return service.createOrder(
                request,
                email);
    }

    @GetMapping("/my-orders")
    public List<OrderResponse>
    getMyOrders(

            HttpServletRequest request) {

        String authHeader =
                request.getHeader(
                        "Authorization"
                );
        System.out.println(
                "Auth Header = " +
                        authHeader
        );
        String token =
                authHeader.substring(7);

        String email =
                jwtService.extractEmail(
                        token
                );
        System.out.println(
                "Email = " + email
        );
        return service
                .getMyOrders(
                        email
                );
    }
}