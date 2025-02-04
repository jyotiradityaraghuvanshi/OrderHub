package com.service.ordering.orders.entity;


import com.service.ordering.orders.Enum.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "user_id" , nullable = false)
    private int userId;

    @Column(name = "cart_id" , nullable = false)
    private int cartId;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "order_status")
    private Status orderStatus = Status.CREATED;

    public void setStatus(Status status) {
        this.orderStatus = status;
    }
}
