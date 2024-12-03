package com.sparta.msa_exam.order.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";
    public static final String ORDER_CANCELLED_ROUTING_KEY = "order.cancelled";

    public static final String ORDER_CREATED_QUEUE = "order.created.queue";
    public static final String ORDER_CANCELLED_QUEUE = "order.cancelled.queue";
    public static final String EXCHANGE = "order.exchange";

    public static final String PRODUCT_QUANTITY_UPDATE_ROUTING_KEY = "product.quantity.update";
    public static final String PRODUCT_QUANTITY_UPDATE_QUEUE = "product.quantity.update.queue";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(ORDER_CREATED_QUEUE, true);
    }

    @Bean
    public Binding orderCreateBinding(Queue orderCreatedQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderCreatedQueue)
                .to(orderExchange)
                .with("order.created");
    }

    @Bean
    public Queue orderCancelledQueue() {
        return new Queue(ORDER_CANCELLED_QUEUE, true);
    }

    @Bean
    public Binding orderCancelledBinding(Queue orderCancelledQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderCancelledQueue)
                .to(orderExchange)
                .with("order.*");
    }

    @Bean
    public Queue productQuantityUpdateQueue() {
        return new Queue(PRODUCT_QUANTITY_UPDATE_QUEUE, true);
    }

    @Bean
    public Binding productQuantityUpdateBinding(Queue productQuantityUpdateQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(productQuantityUpdateQueue)
                .to(orderExchange)
                .with(PRODUCT_QUANTITY_UPDATE_ROUTING_KEY);
    }
}
