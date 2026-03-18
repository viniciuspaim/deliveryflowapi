package dev.viniciuspaim.minimalapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_restaurants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long restaurantId;
    RestaurantStatusEnum status;
    String fullName;
    String fiscalCode;
    String phoneNumber;
    String email;
    String address;
    String city;
    String state;
    String zipCode;
    String country;
    String description;
    LocalDateTime createdAt;
}
