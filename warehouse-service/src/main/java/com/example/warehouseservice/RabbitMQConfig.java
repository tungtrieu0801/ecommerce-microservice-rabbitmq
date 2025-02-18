//package com.example.warehouseservice;
//
//import org.springframework.amqp.core.*;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import static com.example.warehouseservice.consts.VariableConst.*;
//
//@Configurable
//public class RabbitMQConfig {
//
//    @Bean
//    DirectExchange orderExchange() {
//        return new DirectExchange(ORDER_EXCHANGE);
//    }
//
//    @Bean
//    FanoutExchange order_fanout_exchange() {
//        return new FanoutExchange(ORDER_FANOUT_EXCHANGE);
//    }
//
//    @Bean
//    Queue paymentQueue() {
//        return new Queue(PAYMENT_QUEUE, true);
//    }
//
//    @Bean
//    Queue shippingQueue() {
//        return new Queue(SHIPPING_QUEUE, true);
//    }
//
//    @Bean
//    Queue notificationQueue() {
//        return new Queue(NOTIFICATION_QUEUE, true);
//    }
//
//    @Bean
//    Binding bindingPayment(Queue paymentQueue, DirectExchange orderExchange) {
//        return BindingBuilder.bind(paymentQueue).to(orderExchange).with("order.payment");
//    }
//
//    @Bean
//    Binding bindingShipping(Queue shippingQueue, DirectExchange orderExchange) {
//        return BindingBuilder.bind(shippingQueue).to(orderExchange).with("order.shipping");
//    }
//
//    @Bean
//    Binding bindingNotification(Queue notificationQueue, DirectExchange orderExchange) {
//        return BindingBuilder.bind(notificationQueue).to(orderExchange).with("order.notification");
//    }
//
//    @Bean
//    public Binding bindWarehouseQueue(Queue warehouseQueue, FanoutExchange orderFanoutExchange) {
//        return BindingBuilder.bind(warehouseQueue).to(orderFanoutExchange);
//    }
//
//    @Bean
//    public Binding bindCustomerServiceQueue(Queue customerServiceQueue, FanoutExchange orderFanoutExchange) {
//        return BindingBuilder.bind(customerServiceQueue).to(orderFanoutExchange);
//    }
//
//    @Bean
//    public Binding bindAnalyticsQueue(Queue analyticsQueue, FanoutExchange orderFanoutExchange) {
//        return BindingBuilder.bind(analyticsQueue).to(orderFanoutExchange);
//    }
//
//
//    public static final String WAREHOUSE_EXCHANGE = "warehouse_exchange";
//    public static final String WAREHOUSE_QUEUE = "warehouse_queue";
//    public static final String WAREHOUSE_ROUTING_KEY = "warehouse.request";
//
//    @Bean
//    DirectExchange warehouseExchange() {
//        return new DirectExchange(WAREHOUSE_EXCHANGE);
//    }
//
//    @Bean
//    Queue warehouseQueue() {
//        return new Queue(WAREHOUSE_QUEUE, true);
//    }
//
//    @Bean
//    Binding warehouseBinding(Queue warehouseQueue, DirectExchange warehouseExchange) {
//        return BindingBuilder.bind(warehouseQueue).to(warehouseExchange).with(WAREHOUSE_ROUTING_KEY);
//    }
//
//}
