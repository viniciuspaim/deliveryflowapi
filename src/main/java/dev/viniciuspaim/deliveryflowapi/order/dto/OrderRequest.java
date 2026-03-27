package dev.viniciuspaim.deliveryflowapi.order.dto;

import lombok.Data;

@Data
public class OrderRequest {
    Long customerId;
    Long restaurantId;
}
