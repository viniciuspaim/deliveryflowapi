package dev.viniciuspaim.minimalapi.service;

import dev.viniciuspaim.minimalapi.dto.RestaurantRequest;
import dev.viniciuspaim.minimalapi.model.Order;
import dev.viniciuspaim.minimalapi.model.Restaurant;
import dev.viniciuspaim.minimalapi.model.RestaurantStatusEnum;
import dev.viniciuspaim.minimalapi.repository.OrderRepository;
import dev.viniciuspaim.minimalapi.repository.RestaurantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RestaurantService {
    final
    RestaurantRepository restaurantRepository;
    final
    OrderRepository orderRepository;

    public RestaurantService(OrderRepository orderRepository, RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
    }

    public Page<Order> getOrdersByRestaurant(Long restaurantId, int page){
        Pageable pageable = PageRequest.of(page, 10);
        return orderRepository.findAllByRestaurantRestaurantId(restaurantId, pageable);
    }

    public Restaurant createRestaurant(RestaurantRequest request){
        Restaurant restaurant = Restaurant.builder()
                .fullName(request.getFullName())
                .fiscalCode(request.getFiscalCode())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .zipCode(request.getZipCode())
                .createdAt(LocalDateTime.now())
                .status(RestaurantStatusEnum.CLOSED)
                .build();

        return restaurantRepository.save(restaurant);
    }
    public Restaurant checkStatus(Long restaurantId){
        return null;
    }
    public Restaurant openRestaurant(Long restaurantId, RestaurantRequest request){
        return null;
    }
}
