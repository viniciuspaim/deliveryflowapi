package dev.viniciuspaim.deliveryflowapi.customer;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long customerId;
        String fullName;
        String idNumber;
        String email;
        String phoneNumber;
        String address;
        String city;
        String state;
        String zipCode;
        String country;
        BigDecimal totalSpent;
        LocalDateTime createdAt;
    }

