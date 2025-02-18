package com.example.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.orderservice.consts.VariableConst.*;

@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    DirectExchange orderExchange() {
        System.out.println("Creating Exchange: ORDER_EXCHANGE");
        return new DirectExchange(ORDER_EXCHANGE);
    }

    @Bean
    FanoutExchange order_fanout_exchange() {
        return new FanoutExchange(ORDER_FANOUT_EXCHANGE);
    }

    @Bean
    Queue paymentQueue() {
        return new Queue(PAYMENT_QUEUE, true);
    }

    @Bean
    Queue shippingQueue() {
        return new Queue(SHIPPING_QUEUE, true);
    }

    @Bean
    Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    Binding bindingPayment(Queue paymentQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(paymentQueue).to(orderExchange).with("order.payment");
    }

    @Bean
    Binding bindingShipping(Queue shippingQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(shippingQueue).to(orderExchange).with("order.shipping");
    }

    @Bean
    Binding bindingNotification(Queue notificationQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(notificationQueue).to(orderExchange).with("order.notification");
    }

    @Bean
    public Binding bindWarehouseQueue(Queue warehouseQueue, FanoutExchange orderFanoutExchange) {
        return BindingBuilder.bind(warehouseQueue).to(orderFanoutExchange);
    }

//    @Bean
//    public Binding bindCustomerServiceQueue(Queue customerServiceQueue, FanoutExchange orderFanoutExchange) {
//        return BindingBuilder.bind(customerServiceQueue).to(orderFanoutExchange);
//    }
//
//    @Bean
//    public Binding bindAnalyticsQueue(Queue analyticsQueue, FanoutExchange orderFanoutExchange) {
//        return BindingBuilder.bind(analyticsQueue).to(orderFanoutExchange);
//    }

    public static final String WAREHOUSE_EXCHANGE = "warehouse_exchange";
    public static final String WAREHOUSE_QUEUE = "warehouse_queue";
    public static final String WAREHOUSE_ROUTING_KEY = "warehouse.request";

    @Bean
    DirectExchange warehouseExchange() {
        return new DirectExchange(WAREHOUSE_EXCHANGE);
    }

    @Bean
    Queue warehouseQueue() {
        return new Queue(WAREHOUSE_QUEUE, true);
    }

    @Bean
    Binding warehouseBinding(Queue warehouseQueue, DirectExchange warehouseExchange) {
        return BindingBuilder.bind(warehouseQueue).to(warehouseExchange).with(WAREHOUSE_ROUTING_KEY);
    }
}
