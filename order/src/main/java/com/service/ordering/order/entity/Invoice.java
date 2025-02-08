package com.service.ordering.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;

    @OneToOne
    @JoinColumn(name = "order_id") // Foreign key to Order entity
    private Order order;

    private LocalDateTime generatedAt;

    // Getters and Setters
}
