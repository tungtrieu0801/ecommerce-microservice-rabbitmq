package com.example.notificationservice.service;

import lombok.Data;

@Data
public class Order {
    private String orderId;
    private String status;
    private String orderType;

    // Constructor, Getter, Setter
}
