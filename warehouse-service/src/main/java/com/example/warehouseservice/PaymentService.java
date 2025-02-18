package com.example.warehouseservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class PaymentService {

    private final RabbitTemplate rabbitTemplate;
    private static final String WAREHOUSE_QUEUE = "warehouse_queue";
    private static final String REPLY_QUEUE = "warehouse_reply_queue"; // Thêm queue phản hồi

    private static final Map<String, Integer> productStock = new HashMap<>();

    static {
        productStock.put("p1", 10);
        productStock.put("p2", 5);
        productStock.put("p3", 0);
    }

    public PaymentService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

//    @RabbitListener(queues = WAREHOUSE_QUEUE)
//    public Integer handleStockRequest(String productId) {
//        Integer stock = productStock.getOrDefault(productId, 10);
//        System.out.println("Stock for " + productId + ": " + stock);
//        return stock; // Trả về giá trị để RabbitMQ tự động gửi phản hồi
//    }

}

