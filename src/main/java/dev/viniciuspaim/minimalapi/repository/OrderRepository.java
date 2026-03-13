package dev.viniciuspaim.minimalapi.repository;

import dev.viniciuspaim.minimalapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
