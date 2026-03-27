package dev.viniciuspaim.deliveryflowapi.customer;

import dev.viniciuspaim.deliveryflowapi.customer.dto.CustomerRequest;
import dev.viniciuspaim.deliveryflowapi.customer.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .address(request.getAddress())
                .createdAt(LocalDateTime.now())
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
