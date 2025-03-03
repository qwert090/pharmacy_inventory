package com.example.orderservice.service;

import com.example.orderservice.client.MedicationServiceClient;
import com.example.orderservice.dto.CreateOrderDto;
import com.example.orderservice.dto.MedicationResponseDto;
import com.example.orderservice.dto.OrderResponseDto;
import com.example.orderservice.dto.UpdateOrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MedicationServiceClient medicationServiceClient;

    public Order createOrder(CreateOrderDto createOrderDto, UUID userId) {
        return orderRepository.save(Order.builder()
                        .ownerId(userId)
                        .medicationId(createOrderDto.getMedicationId())
                        .count(createOrderDto.getCount())
                .build());
    }

    public List<OrderResponseDto> getOrdersByUser(UUID userId) {
        List<Order> orders = orderRepository.findByOwnerId(userId);
        List<Long> medicationsIds = orders.stream()
                .map(Order::getMedicationId)
                .distinct()
                .toList();
        Map<Long, MedicationResponseDto> medicationResponseDtoMap = medicationServiceClient.getMedicationById(medicationsIds).getBody().stream()
                .collect(Collectors.toMap(MedicationResponseDto::getId, Function.identity()));
        return orders.stream()
                .map(order -> OrderResponseDto.builder()
                        .id(order.getId())
                        .count(order.getCount())
                        .medicationsName(medicationResponseDtoMap.get(order.getMedicationId()).getName())
                        .medicationsCategory(medicationResponseDtoMap.get(order.getMedicationId()).getCategory())
                        .build())
                .toList();
    }

    public Order updateOrder(Long orderId, UpdateOrderDto updateOrderDto, UUID userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!order.getOwnerId().equals(userId)) {
            throw new SecurityException("Unauthorized access to order");
        }
        order.setMedicationId(updateOrderDto.getMedicationId());
        order.setCount(updateOrderDto.getCount());
        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId, UUID userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!order.getOwnerId().equals(userId)) {
            throw new SecurityException("Unauthorized access to order");
        }
        orderRepository.delete(order);
    }
}
