package com.example.orderservice;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam String orderId) {
        orderProducer.sendOrder(orderId, "order.payment");
        orderProducer.sendOrder(orderId, "order.shipping");
        orderProducer.sendOrder(orderId, "order.notification");
        orderProducer.notifyAllServices(orderId);
        return "✅ Order " + orderId + " sent!";
    }

    @GetMapping()
    public Integer get(@RequestParam String orderId) {
        return orderProducer.check(orderId);
    }
}
