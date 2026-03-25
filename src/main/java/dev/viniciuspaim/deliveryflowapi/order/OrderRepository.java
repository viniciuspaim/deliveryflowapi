package dev.viniciuspaim.deliveryflowapi.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByRestaurantRestaurantId(Long restaurantId);
}
