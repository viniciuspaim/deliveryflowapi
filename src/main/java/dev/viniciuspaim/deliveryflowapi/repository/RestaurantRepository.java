package dev.viniciuspaim.deliveryflowapi.repository;

import dev.viniciuspaim.deliveryflowapi.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
