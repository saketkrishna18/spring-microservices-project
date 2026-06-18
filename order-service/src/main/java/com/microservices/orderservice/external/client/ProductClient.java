package com.microservices.orderservice.external.client;

import com.microservices.orderservice.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponse getProductById(
            @PathVariable Long id);

    @PutMapping("/products/reduce-stock/{id}")
    String reduceStock(
            @PathVariable Long id,
            @RequestParam Integer quantity);
}