package dev.viniciuspaim.deliveryflowapi.dto;

import lombok.Data;

@Data
public class CustomerRequest {
    Long customerId;
    String fullName;
    String idNumber;
    String email;
}
