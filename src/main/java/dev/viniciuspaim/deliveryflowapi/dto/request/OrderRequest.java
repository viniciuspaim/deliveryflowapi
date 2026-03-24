package dev.viniciuspaim.deliveryflowapi.dto.request;

import lombok.Data;

@Data
public class OrderRequest {
    Long customerId;
    Long restaurantId;
}
