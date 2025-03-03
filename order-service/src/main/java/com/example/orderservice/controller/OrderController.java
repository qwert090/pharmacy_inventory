package com.example.orderservice.controller;

import com.example.orderservice.client.AuthServiceClient;
import com.example.orderservice.dto.CreateOrderDto;
import com.example.orderservice.dto.OrderResponseDto;
import com.example.orderservice.dto.SessionDto;
import com.example.orderservice.dto.UpdateOrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;

import com.example.orderservice.util.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final AuthServiceClient authServiceClient;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt,
                                             @RequestBody CreateOrderDto createOrderDto) {
        String token = ApplicationUtils.extractJwtFromBearerToken(jwt);
        SessionDto sessionDto = authServiceClient.getSession(token).getBody();
        UUID userId = sessionDto.getUserId();
        Order order = orderService.createOrder(createOrderDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrders(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        String token = ApplicationUtils.extractJwtFromBearerToken(jwt);
        SessionDto sessionDto = authServiceClient.getSession(token).getBody();
        UUID userId = sessionDto.getUserId();
        List<OrderResponseDto> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt,
                                             @PathVariable Long orderId,
                                             @RequestBody UpdateOrderDto updateOrderDto) {
        String token = ApplicationUtils.extractJwtFromBearerToken(jwt);
        SessionDto sessionDto = authServiceClient.getSession(token).getBody();
        UUID userId = sessionDto.getUserId();
        Order updatedOrder = orderService.updateOrder(orderId, updateOrderDto, userId);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt,
                                            @PathVariable Long orderId) {
        String token = ApplicationUtils.extractJwtFromBearerToken(jwt);
        SessionDto sessionDto = authServiceClient.getSession(token).getBody();
        UUID userId = sessionDto.getUserId();
        orderService.deleteOrder(orderId, userId);
        return ResponseEntity.noContent().build();
    }
}
