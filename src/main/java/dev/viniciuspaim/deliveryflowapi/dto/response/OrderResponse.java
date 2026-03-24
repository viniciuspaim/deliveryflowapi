package dev.viniciuspaim.deliveryflowapi.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderResponse {
    Long orderId;
    Long customerId;
    Long restaurantId;
    String orderStatus;
    BigDecimal orderAmount;
    LocalDateTime orderDate;
}
