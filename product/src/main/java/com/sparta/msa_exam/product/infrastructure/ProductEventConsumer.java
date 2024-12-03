package com.sparta.msa_exam.product.infrastructure;

import com.sparta.msa_exam.product.controller.dto.OrderCancelledEvent;
import com.sparta.msa_exam.product.event.ProductQuantityUpdateEvent;
import com.sparta.msa_exam.product.infrastructure.config.RabbitMQConfig;
import com.sparta.msa_exam.product.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventConsumer {

    private final ProductService productService;

    public ProductEventConsumer(ProductService productService) {
        this.productService = productService;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CANCELLED_QUEUE)
    public void handleOrderCancelledEvent(OrderCancelledEvent event) {
        for (var orderProduct : event.getOrderProductDtoList()) {
            productService.addProductQuantity(orderProduct.getProductId(), orderProduct.getQuantity());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCT_QUANTITY_UPDATE_QUEUE)
    public void handleProductQuantityUpdateEvent(ProductQuantityUpdateEvent event) {
        productService.reduceProductQuantity(event.getProductId(), event.getQuantity());
    }
}
