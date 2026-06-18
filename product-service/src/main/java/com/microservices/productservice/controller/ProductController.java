package com.microservices.productservice.controller;

import com.microservices.productservice.dto.ProductRequest;
import com.microservices.productservice.dto.ProductResponse;
import com.microservices.productservice.entity.Product;
import com.microservices.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ProductResponse saveProduct(
            @Valid
            @RequestBody ProductRequest request) {

        return service.saveProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(
            @PathVariable Long id) {

        return service.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(
            @PathVariable Long id) {

        service.deleteProduct(id);

        return "Product deleted successfully";
    }
    @PutMapping("/reduce-stock/{id}")
    public String reduceStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {

        service.reduceStock(id, quantity);

        return "Stock updated successfully";
    }

    @PutMapping("/{id}")
    public Product
    updateProduct(

            @PathVariable
            Long id,

            @RequestBody
            Product product
    ) {

        return service
                .updateProduct(
                        id,
                        product
                );
    }
}