package dev.viniciuspaim.minimalapi.service;

import dev.viniciuspaim.minimalapi.dto.OrderRequest;
import dev.viniciuspaim.minimalapi.exception.CustomerNotFoundException;
import dev.viniciuspaim.minimalapi.exception.InvalidOrderStatusException;
import dev.viniciuspaim.minimalapi.exception.OrderNotFoundException;
import dev.viniciuspaim.minimalapi.messaging.OrderEventProducer;
import dev.viniciuspaim.minimalapi.model.Customer;
import dev.viniciuspaim.minimalapi.model.Order;
import dev.viniciuspaim.minimalapi.model.OrderStatusEnum;
import dev.viniciuspaim.minimalapi.repository.CustomerRepository;
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
    final
    CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, OrderEventProducer orderEventProducer, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
        this.customerRepository = customerRepository;
    }

    public Order createOrder(OrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Invalid Customer Id: " + request.getCustomerId()));

        Order order = Order.builder()
                .customer(customer)
                .totalAmount(request.getTotalAmount())
                .status(OrderStatusEnum.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        return orderRepository.save(order);
    }

    public void validateNotCancelled(Order order) {
        if (order.getStatus() == OrderStatusEnum.CANCELLED) {
            throw new InvalidOrderStatusException("Order " + order.getOrderId() + " is already cancelled");
        }
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
        validateNotCancelled(order);
        order.setStatus(OrderStatusEnum.CANCELLED);
        return orderRepository.save(order);
    }

    public Order confirmOrder(Long orderId) {
        Order order = getOrderById(orderId);
        validateNotCancelled(order);
        order.setStatus(OrderStatusEnum.CONFIRMED);
        Order saved = orderRepository.save(order);
        orderEventProducer.sendOrderConfirmedEvent(saved.getOrderId());
        return saved;
    }
}
