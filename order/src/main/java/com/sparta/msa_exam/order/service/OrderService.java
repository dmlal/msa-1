package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.client.dto.GetProductInfoResponseDto;
import com.sparta.msa_exam.order.common.exception.CustomException;
import com.sparta.msa_exam.order.common.exception.ErrorCode;
import com.sparta.msa_exam.order.controller.dto.CreateOrderRequestDto;
import com.sparta.msa_exam.order.controller.dto.CreateOrderResponseDto;
import com.sparta.msa_exam.order.controller.dto.GetOrderResponseDto;
import com.sparta.msa_exam.order.controller.dto.OrderProductDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.entity.enums.OrderStatus;
import com.sparta.msa_exam.order.event.OrderCreateEvent;
import com.sparta.msa_exam.order.infrastructure.config.RabbitMQConfig;
import com.sparta.msa_exam.order.infrastructure.messaging.MessageQueuePublisher;
import com.sparta.msa_exam.order.repository.OrderRepository;
import com.sparta.msa_exam.order.vo.Price;
import com.sparta.msa_exam.order.vo.Quantity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final MessageQueuePublisher messageQueuePublisher;
    private final EntityManager entityManager;


    @Transactional
    public CreateOrderResponseDto createOrder(Long userId, CreateOrderRequestDto requestDto) {
        Order order = Order.builder()
                .userId(userId)
                .orderProductList(new ArrayList<>())
                .orderStatus(OrderStatus.PENDING)
                .build();

        order = orderRepository.saveAndFlush(order);

        List<Long> failedProductList = new ArrayList<>();

        for (Long productId : requestDto.getProductIdList()) {
            try {
                System.out.println("productId = " + productId);
                GetProductInfoResponseDto responseDto = productClient.getProductInfo(productId);
                System.out.println("responseDto = " + responseDto);
                OrderProduct orderProduct = new OrderProduct(order, productId, Price.of(responseDto.getPrice()), Quantity.of(responseDto.getQuantity()));
                order.addOrderProduct(orderProduct);
            } catch (CustomException e) {
                System.out.println("productId = " + productId);
                System.out.println("e.getMessage() = " + e.getMessage());
                failedProductList.add(productId);
            }
        }

        orderRepository.save(order);
        List<OrderProductDto> orderProductDtoList =
                Optional.ofNullable(order.getOrderProductList())
                        .orElse(Collections.emptyList()).stream()
                .map(orderProduct -> new OrderProductDto(
                        orderProduct.getProductId(),
                        orderProduct.getQuantity().getQuantity()
                )).toList();

        OrderCreateEvent event = new OrderCreateEvent(order.getId(), userId, orderProductDtoList);

        messageQueuePublisher.publish(RabbitMQConfig.ORDER_CREATED_ROUTING_KEY, event);

        return new CreateOrderResponseDto(order.getId(), userId, order.getTotalPrice(), failedProductList);
    }

    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = getOrder(orderId);
        order.updateOrderStatus(orderStatus);
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }

    public GetProductInfoResponseDto getProductInfo(Long productId) {
        return productClient.getProductInfo(productId);
    }

    @Cacheable(cacheNames = "orderInfo", key = "#userId + '-' + #orderId")
    public GetOrderResponseDto getOrderInfo(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        return new GetOrderResponseDto(orderId, userId, order.getTotalPrice(), order.getOrderStatus());
    }
}
