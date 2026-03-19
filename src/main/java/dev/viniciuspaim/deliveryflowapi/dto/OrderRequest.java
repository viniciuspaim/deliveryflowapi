package dev.viniciuspaim.deliveryflowapi.dto;

import lombok.Data;

@Data
public class OrderRequest {
    Long customerId;
    Long restaurantId;
}
