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

    private LocalDateTime generatedAt;

    @OneToOne
    @JoinColumn(name = "order_id") // Foreign key in the Invoice Table
    private Order order;

    // Getters and Setters
}
