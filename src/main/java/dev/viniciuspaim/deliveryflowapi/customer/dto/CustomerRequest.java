package dev.viniciuspaim.deliveryflowapi.customer.dto;

import lombok.Data;

@Data
public class CustomerRequest {
    String fullName;
    String idNumber;
    String email;
    String phoneNumber;
    String address;
}
