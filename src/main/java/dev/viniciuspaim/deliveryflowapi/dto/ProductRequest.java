package dev.viniciuspaim.deliveryflowapi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    String name;
    String description;
    BigDecimal price;
    Long categoryId;
    Long restaurantId;
}
