package dev.viniciuspaim.minimalapi.repository;

import dev.viniciuspaim.minimalapi.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByRestaurantRestaurantId(Long restaurantId, Pageable pageable);
}
