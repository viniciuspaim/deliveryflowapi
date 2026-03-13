package dev.viniciuspaim.minimalapi.service;

import dev.viniciuspaim.minimalapi.dto.CustomerRequest;
import dev.viniciuspaim.minimalapi.exception.CustomerNotFoundException;
import dev.viniciuspaim.minimalapi.model.Customer;
import dev.viniciuspaim.minimalapi.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerRequest request) {
        Customer customer = Customer.builder()
                .customerId(request.getCustomerId())
                .fullName(request.getFullName())
                .idNumber(request.getIdNumber())
                .email(request.getEmail())
                .build();

        return customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Invalid Customer Id: " + id));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public ResponseEntity<?> deleteById(Long id) {
        customerRepository.deleteById(id);
        return null;
    }
}
