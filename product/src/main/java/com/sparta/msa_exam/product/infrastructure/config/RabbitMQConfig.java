package com.sparta.msa_exam.product.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_CANCELLED_QUEUE = "order.cancelled.queue";
    public static final String EXCHANGE = "order.exchange";
    public static final String PRODUCT_QUANTITY_UPDATE_QUEUE = "product.quantity.update.queue";


    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue productOrderCancelledQueue() {
        return new Queue(ORDER_CANCELLED_QUEUE, true);
    }

    @Bean
    public Binding productOrderCancelledQueue(Queue productOrderCancelledQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(productOrderCancelledQueue).to(orderExchange).with("order.*");
    }

    @Bean
    public Queue productQuantityUpdateQueue() {
        return new Queue(PRODUCT_QUANTITY_UPDATE_QUEUE, true);
    }
}
