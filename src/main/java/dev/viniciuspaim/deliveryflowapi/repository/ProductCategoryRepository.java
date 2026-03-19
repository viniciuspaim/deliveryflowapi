package dev.viniciuspaim.deliveryflowapi.repository;

import dev.viniciuspaim.deliveryflowapi.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
}
