package dev.viniciuspaim.deliveryflowapi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {
    Long customerId;
    BigDecimal totalAmount;
}
