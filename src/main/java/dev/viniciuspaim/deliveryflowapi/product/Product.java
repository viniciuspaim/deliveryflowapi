package dev.viniciuspaim.deliveryflowapi.product;

import dev.viniciuspaim.deliveryflowapi.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId;
    String name;
    String description;
    BigDecimal price;
    ProductStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "restaurantId")
    Restaurant restaurant;
}
