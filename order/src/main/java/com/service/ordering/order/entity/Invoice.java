package com.service.ordering.order.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.service.ordering.order.dto.ResponseDto.InvoiceItemDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;

    @Column(name = "generate_time")
    private LocalDateTime generatedAt;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String userMail;

    @Column(nullable = false)
    private Integer totalAmount;

    @OneToOne
    @JoinColumn(name = "order_id") // Foreign key in the Invoice Table
    @JsonProperty
    private Order order;

// Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
