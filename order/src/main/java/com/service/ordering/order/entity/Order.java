package com.service.ordering.order.entity;


import com.service.ordering.order.Enum.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Data
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

    @Column(name = "user_email" , nullable = false)
    private String userMail;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(nullable = false)
    private Integer totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private Status orderStatus = Status.CREATED;

    @Column(name = "time_status")
    private LocalDateTime createdAt;

    @Column(name = "merchant_id")
    private List<Integer> merchantIds;


    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    List<OrderItem> orderItemsList;


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

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public List<Integer> getMerchantIds() {
        return merchantIds;
    }

    public void setMerchantIds(List<Integer> merchantIds) {
        this.merchantIds = merchantIds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
