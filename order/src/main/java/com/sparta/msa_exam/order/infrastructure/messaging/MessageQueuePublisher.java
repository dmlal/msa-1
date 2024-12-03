package com.sparta.msa_exam.order.infrastructure.messaging;

import com.sparta.msa_exam.order.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageQueuePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(String routingKey, Object message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, routingKey, message);
    }
}
