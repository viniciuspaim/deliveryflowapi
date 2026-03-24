package dev.viniciuspaim.deliveryflowapi.service;

import dev.viniciuspaim.deliveryflowapi.dto.request.OrderRequest;
import dev.viniciuspaim.deliveryflowapi.dto.response.OrderResponse;
import dev.viniciuspaim.deliveryflowapi.exception.InvalidOrderStatusException;
import dev.viniciuspaim.deliveryflowapi.exception.OrderNotFoundException;
import dev.viniciuspaim.deliveryflowapi.messaging.OrderEventProducer;
import dev.viniciuspaim.deliveryflowapi.model.Customer;
import dev.viniciuspaim.deliveryflowapi.model.Order;
import dev.viniciuspaim.deliveryflowapi.model.OrderStatusEnum;
import dev.viniciuspaim.deliveryflowapi.model.Restaurant;
import dev.viniciuspaim.deliveryflowapi.repository.OrderRepository;
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
    CustomerService customerService;
    final
    RestaurantService restaurantService;

    public OrderService(OrderRepository orderRepository,
                        OrderEventProducer orderEventProducer,
                        CustomerService customerService,
                        RestaurantService restaurantService) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
        this.customerService = customerService;
        this.restaurantService = restaurantService;
    }

    public Order createOrder(OrderRequest request) {
        Customer customer = customerService.findById(request.getCustomerId());
        Restaurant restaurant = restaurantService.findById(request.getRestaurantId());

        Order order = Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .orderStatus(OrderStatusEnum.CREATED)
                .orderDate(LocalDateTime.now())
                .build();

        return orderRepository.save(order);
    }

    private void validateNotCancelled(Order order) {
        if (order.getOrderStatus() == OrderStatusEnum.CANCELLED) {
            throw new InvalidOrderStatusException("Order " + order.getOrderId() + " is already cancelled");
        }
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("This Order " + orderId + " is invalid or does not exist"));
    }

    public OrderResponse getOrderResponseById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::toResponse)
                .orElseThrow(() -> new OrderNotFoundException("This Order " + orderId + " is invalid or does not exist"));
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Order cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        validateNotCancelled(order);
        order.setOrderStatus(OrderStatusEnum.CANCELLED);
        return orderRepository.save(order);
    }

    public List<OrderResponse> getOrdersByRestaurant(Long restaurantId){
        return orderRepository.findAllByRestaurantRestaurantId(restaurantId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Order confirmOrder(Long orderId) {
        Order order = getOrderById(orderId);
        validateNotCancelled(order);
        order.setOrderStatus(OrderStatusEnum.CONFIRMED);
        Order saved = orderRepository.save(order);
        orderEventProducer.sendOrderConfirmedEvent(saved.getOrderId());
        return saved;
    }

    private OrderResponse toResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setCustomerId(order.getCustomer().getCustomerId());
        orderResponse.setRestaurantId(
                order.getRestaurant() != null ? order.getRestaurant().getRestaurantId() : null
        );
        orderResponse.setOrderStatus(order.getOrderStatus().toString());
        orderResponse.setOrderAmount(order.getOrderAmount());
        orderResponse.setOrderDate(order.getOrderDate());
        return orderResponse;
    }
}
