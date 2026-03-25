package dev.viniciuspaim.deliveryflowapi.order;

import dev.viniciuspaim.deliveryflowapi.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    Order order;

    @ManyToOne
    @JoinColumn(name = "productId")
    Product product;

    int quantity;
    BigDecimal unitPrice;
}
