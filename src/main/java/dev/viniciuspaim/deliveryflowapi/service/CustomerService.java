package dev.viniciuspaim.deliveryflowapi.service;

import dev.viniciuspaim.deliveryflowapi.dto.CustomerRequest;
import dev.viniciuspaim.deliveryflowapi.exception.CustomerNotFoundException;
import dev.viniciuspaim.deliveryflowapi.model.Customer;
import dev.viniciuspaim.deliveryflowapi.repository.CustomerRepository;
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

    public void deleteById(Long id) {
        Customer customer = findById(id);
        customerRepository.delete(customer);
    }
}
