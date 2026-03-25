package dev.viniciuspaim.deliveryflowapi.restaurant;

import dev.viniciuspaim.deliveryflowapi.dto.request.RestaurantRequest;
import dev.viniciuspaim.deliveryflowapi.exception.RestaurantNotFoundException;
import dev.viniciuspaim.deliveryflowapi.enums.RestaurantStatusEnum;
// TODO: replace direct OrderRepository dependency with OrderService to isolate domain boundaries
import dev.viniciuspaim.deliveryflowapi.order.OrderRepository;
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

    public Restaurant findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Invalid Restaurant Id: " + restaurantId));
    }
//    Not implemented yet!!!
//    public Restaurant checkStatus(Long restaurantId){
//        return null;
//    }
//    public Restaurant openRestaurant(Long restaurantId, RestaurantRequest request){
//        return null;
//    }
}
