package dev.viniciuspaim.deliveryflowapi.repository;

import dev.viniciuspaim.deliveryflowapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
