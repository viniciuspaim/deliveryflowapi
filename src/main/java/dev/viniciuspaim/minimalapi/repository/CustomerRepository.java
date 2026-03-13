package dev.viniciuspaim.minimalapi.repository;

import dev.viniciuspaim.minimalapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
