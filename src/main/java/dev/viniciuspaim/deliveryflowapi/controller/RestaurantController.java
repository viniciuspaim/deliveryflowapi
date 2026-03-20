package dev.viniciuspaim.deliveryflowapi.controller;

import dev.viniciuspaim.deliveryflowapi.dto.RestaurantRequest;
import dev.viniciuspaim.deliveryflowapi.service.OrderService;
import dev.viniciuspaim.deliveryflowapi.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/restaurants")
@RestController
public class RestaurantController {
    final RestaurantService restaurantService;
    private final OrderService orderService;

    public RestaurantController(RestaurantService restaurantService, OrderService orderService) {
     this.restaurantService = restaurantService;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantService.createRestaurant(restaurantRequest));
    }

    @GetMapping("/{restaurantId}/orders")
    public ResponseEntity<?> getOrdersByRestaurant(
            @PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getOrdersByRestaurant(restaurantId));
    }
}
