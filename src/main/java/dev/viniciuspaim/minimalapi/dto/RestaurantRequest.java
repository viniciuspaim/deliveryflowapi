package dev.viniciuspaim.minimalapi.dto;

import lombok.Data;

@Data
public class RestaurantRequest {
    String fullName;
    String fiscalCode;
    String phoneNumber;
    String email;
    String zipCode;
}
