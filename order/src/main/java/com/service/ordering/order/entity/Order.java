package com.service.ordering.order.entity;


import com.service.ordering.order.Enum.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column
    private List<CartItem> cartItemsList;

    @Column(name = "price_map")
    private HashMap<Integer , Integer> priceMap;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }
}
