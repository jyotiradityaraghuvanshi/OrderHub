package com.service.ordering.order.entity;


import com.service.ordering.order.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(name = "user_id" , nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer totalAmount;

    @Column(name = "order_status")
    private Status orderStatus = Status.CREATED;



}
