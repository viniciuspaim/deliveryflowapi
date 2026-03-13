package dev.viniciuspaim.minimalapi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {
    Long customerId;
    BigDecimal totalAmount;
}
