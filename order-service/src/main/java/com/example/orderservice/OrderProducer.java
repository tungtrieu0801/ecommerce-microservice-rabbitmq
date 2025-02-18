package com.example.orderservice;

import com.example.orderservice.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.example.orderservice.consts.VariableConst.ORDER_EXCHANGE;

@Service
public class OrderProducer {

    private static final String WAREHOUSE_EXCHANGE = "warehouse_exchange";
    private static final String WAREHOUSE_ROUTING_KEY = "warehouse.request";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder(String orderId, String routingKey) {
        Map<String, Object> orderMessage = Map.of(
                "orderId", orderId,
                "status", "NEW"
        );
        rabbitTemplate.convertAndSend(ORDER_EXCHANGE, routingKey, orderMessage);
        System.out.println("✅ Sent order: " + orderMessage + " to " + routingKey);
    }

    public void notifyAllServices(String orderId) {
        rabbitTemplate.convertAndSend("order_fanout_exchange", "", "New Order " + orderId);
    }

    public Integer check(String orderId) {
        System.out.println("Sending request for orderId: " + orderId);
        Integer stock = (Integer) rabbitTemplate.convertSendAndReceive(
                WAREHOUSE_EXCHANGE, WAREHOUSE_ROUTING_KEY, orderId);
        System.out.println("Received stock: " + stock);
        return stock;
    }

}
