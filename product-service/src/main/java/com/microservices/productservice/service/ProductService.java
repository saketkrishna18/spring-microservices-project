package com.microservices.productservice.service;

import com.microservices.productservice.dto.ProductRequest;
import com.microservices.productservice.dto.ProductResponse;
import com.microservices.productservice.entity.Product;
import com.microservices.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponse saveProduct(
            ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        Product savedProduct =
                repository.save(product);

        return mapToResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {

        Product product =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        return mapToResponse(product);
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    private ProductResponse mapToResponse(
            Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    public void reduceStock(
            Long productId,
            Integer quantity) {

        Product product =
                repository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        if (product.getQuantity()
                < quantity) {

            throw new RuntimeException(
                    "Insufficient stock");
        }

        product.setQuantity(
                product.getQuantity()
                        - quantity);

        repository.save(product);
    }

    public Product  updateProduct(

            Long id,
            Product updatedProduct
    ) {

        Product existing =
                repository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Product not found"
                                        )
                        );

        existing.setName(
                updatedProduct
                        .getName()
        );

        existing.setPrice(
                updatedProduct
                        .getPrice()
        );

        existing.setQuantity(
                updatedProduct
                        .getQuantity()
        );

        return repository
                .save(
                        existing
                );
    }
}