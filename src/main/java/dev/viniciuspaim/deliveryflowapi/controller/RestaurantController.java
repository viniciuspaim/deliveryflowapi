package dev.viniciuspaim.deliveryflowapi.controller;

import dev.viniciuspaim.deliveryflowapi.dto.RestaurantRequest;
import dev.viniciuspaim.deliveryflowapi.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/restaurants")
@RestController
public class RestaurantController {
    final RestaurantService restaurantService;
    public RestaurantController(RestaurantService restaurantService) {
     this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantService.createRestaurant(restaurantRequest));
    }

    @GetMapping("/{restaurantId}/orders")
    public ResponseEntity<?> getOrdersByRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(restaurantService.getOrdersByRestaurant(restaurantId, page));
    }
}
