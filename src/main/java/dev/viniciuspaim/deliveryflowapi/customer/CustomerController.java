package dev.viniciuspaim.deliveryflowapi.customer;

import dev.viniciuspaim.deliveryflowapi.dto.request.CustomerRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/customers")
@RestController
public class CustomerController {
    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customer) {
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.findById(customerId));
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteById(customerId);
        return ResponseEntity.noContent().build();
    }
}
