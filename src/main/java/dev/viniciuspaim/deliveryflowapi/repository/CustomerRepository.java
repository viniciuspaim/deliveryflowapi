package dev.viniciuspaim.deliveryflowapi.repository;

import dev.viniciuspaim.deliveryflowapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
