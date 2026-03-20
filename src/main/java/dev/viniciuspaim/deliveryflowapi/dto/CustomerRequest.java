package dev.viniciuspaim.deliveryflowapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerRequest {
    String fullName;
    String idNumber;
    String email;
    String phoneNumber;
    String address;
}
