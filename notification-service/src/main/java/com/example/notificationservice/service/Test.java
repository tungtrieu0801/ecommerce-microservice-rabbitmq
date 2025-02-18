package com.example.notificationservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Test {

    @RabbitListener(queues = "notification_queue")
    public void receivePaymentOrder(Order order) {
        System.out.println("✅ Received payment order: " + order);

    }

}
