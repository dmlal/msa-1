package com.sparta.msa_exam.order.infrastructure;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.common.exception.CustomException;
import com.sparta.msa_exam.order.controller.dto.OrderProductDto;
import com.sparta.msa_exam.order.entity.enums.OrderStatus;
import com.sparta.msa_exam.order.event.OrderCancelledEvent;
import com.sparta.msa_exam.order.event.OrderCreateEvent;
import com.sparta.msa_exam.order.event.ProductQuantityUpdateEvent;
import com.sparta.msa_exam.order.infrastructure.config.RabbitMQConfig;
import com.sparta.msa_exam.order.infrastructure.messaging.MessageQueuePublisher;
import com.sparta.msa_exam.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    private final ProductClient productClient;
    private final OrderService orderService;
    private final MessageQueuePublisher messageQueuePublisher;

    public OrderEventConsumer(ProductClient productClient, OrderService orderService, MessageQueuePublisher messageQueuePublisher) {
        this.productClient = productClient;
        this.orderService = orderService;
        this.messageQueuePublisher = messageQueuePublisher;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE, containerFactory = "rabbitListenerContainerFactory")
    public void handleOrderCreateEvent(OrderCreateEvent event) {
        boolean allProductsAvailable = true;

        for (OrderProductDto orderProduct : event.getOrderProductDtoList()) {
//            try {
//                productClient.reduceProductQuantity(orderProduct.getProductId(), orderProduct.getQuantity());
//            } catch (CustomException e) {
//                allProductsAvailable = false;
//                e.printStackTrace();
//                break;
//            }
            ProductQuantityUpdateEvent productEvent = new ProductQuantityUpdateEvent(
                    orderProduct.getProductId(),
                    orderProduct.getQuantity()
            );
            messageQueuePublisher.publish(RabbitMQConfig.PRODUCT_QUANTITY_UPDATE_ROUTING_KEY, productEvent);
        }

        if (allProductsAvailable) {
            orderService.updateOrderStatus(event.getOrderId(), OrderStatus.COMPLETED);
        } else {
            orderService.updateOrderStatus(event.getOrderId(), OrderStatus.CANCELLED);

            OrderCancelledEvent cancelledEvent = new OrderCancelledEvent(event.getOrderId(), event.getOrderProductDtoList());
            messageQueuePublisher.publish(RabbitMQConfig.ORDER_CANCELLED_ROUTING_KEY, cancelledEvent);
        }
    }
}
