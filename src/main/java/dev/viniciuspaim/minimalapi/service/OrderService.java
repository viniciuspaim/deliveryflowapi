package dev.viniciuspaim.minimalapi.service;

import dev.viniciuspaim.minimalapi.dto.OrderRequest;
import dev.viniciuspaim.minimalapi.exception.OrderNotFoundException;
import dev.viniciuspaim.minimalapi.messaging.OrderEventProducer;
import dev.viniciuspaim.minimalapi.model.Order;
import dev.viniciuspaim.minimalapi.model.StatusEnum;
import dev.viniciuspaim.minimalapi.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    final
    OrderRepository orderRepository;
    final
    OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository orderRepository, OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
    }

    public Order createOrder(OrderRequest request) {
        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .totalAmount(request.getTotalAmount())
                .status(StatusEnum.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Invalid Order Id: " + orderId));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(StatusEnum.CANCELLED);
        return orderRepository.save(order);
    }

    public Order confirmOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(StatusEnum.CONFIRMED);
        Order saved = orderRepository.save(order);
        orderEventProducer.sendOrderConfirmedEvent(saved.getOrderId());
        return saved;
    }
}
