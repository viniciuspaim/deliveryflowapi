package dev.viniciuspaim.minimalapi.repository;

import dev.viniciuspaim.minimalapi.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
