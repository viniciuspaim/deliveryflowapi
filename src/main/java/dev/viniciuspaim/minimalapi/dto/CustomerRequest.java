package dev.viniciuspaim.minimalapi.dto;

import lombok.Data;

@Data
public class CustomerRequest {
    Long customerId;
    String fullName;
    String idNumber;
    String email;
}
