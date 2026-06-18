package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.dto.OrderResponse;
import com.microservices.orderservice.dto.ProductResponse;
import com.microservices.orderservice.dto.UserResponse;
import com.microservices.orderservice.entity.Order;
import com.microservices.orderservice.exception.InsufficientStockException;
import com.microservices.orderservice.external.client.ProductClient;
import com.microservices.orderservice.external.client.UserClient;
import com.microservices.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductClient productClient;
    private final UserClient userClient;
    public OrderService(
            OrderRepository repository,
            ProductClient productClient,
            UserClient userClient) {

        this.repository =
                repository;

        this.productClient =
                productClient;

        this.userClient =
                userClient;
    }

    public OrderResponse createOrder(
            OrderRequest request,
            String email) {


        System.out.println(
                "Order placed by: " + email);
        UserResponse user =
                userClient
                        .getUserByEmail(
                                email
                        );

        ProductResponse product =
                productClient.getProductById(
                        request.getProductId());

        if (product == null) {
            throw new RuntimeException(
                    "Product not found");
        }

        if (request.getQuantity()
                > product.getQuantity()) {

            throw new InsufficientStockException(
                    "Insufficient stock available");
        }

        // Reduce inventory
        productClient.reduceStock(
                request.getProductId(),
                request.getQuantity());

        double totalPrice =
                product.getPrice()
                        * request.getQuantity();

        Order order = new Order();


        order.setUserId(
                user.getId()
        );


        order.setProductId(
                request.getProductId());

        order.setQuantity(
                request.getQuantity());

        order.setTotalPrice(
                totalPrice);

        Order savedOrder =
                repository.save(order);

        return OrderResponse.builder()
                .id(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .productId(
                        savedOrder.getProductId())
                .quantity(
                        savedOrder.getQuantity())
                .totalPrice(
                        savedOrder.getTotalPrice())
                .build();
    }

    public List<OrderResponse>
    getMyOrders(
            String email) {

        UserResponse user =
                userClient
                        .getUserByEmail(
                                email
                        );

        List<Order> orders =
                repository
                        .findByUserId(
                                user.getId()
                        );

        return orders.stream()
                .map(order -> {

                    ProductResponse
                            product =
                            productClient
                                    .getProductById(
                                            order.getProductId()
                                    );

                    return OrderResponse
                            .builder()
                            .id(
                                    order.getId()
                            )
                            .userId(
                                    order.getUserId()
                            )
                            .productId(
                                    order.getProductId()
                            )
                            .productName(
                                    product.getName()
                            )
                            .quantity(
                                    order.getQuantity()
                            )
                            .totalPrice(
                                    order.getTotalPrice()
                            )
                            .build();
                })
                .toList();
    }
}