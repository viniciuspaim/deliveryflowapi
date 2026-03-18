package dev.viniciuspaim.minimalapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_product_categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long categoryId;
    String categoryName;
}
