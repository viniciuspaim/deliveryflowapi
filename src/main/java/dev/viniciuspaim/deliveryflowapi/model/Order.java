package dev.viniciuspaim.deliveryflowapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;

    @ManyToOne
    @JoinColumn(name = "customerId")
    Customer customer;

    OrderStatusEnum status;
    BigDecimal totalAmount;
    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "restaurantId")
    Restaurant restaurant;

    @OneToMany(mappedBy = "order")
    List<OrderItem> items;
}
