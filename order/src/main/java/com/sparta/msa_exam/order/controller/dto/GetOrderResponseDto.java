package com.sparta.msa_exam.order.controller.dto;

import com.sparta.msa_exam.order.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponseDto {
    private Long orderId;
    private Long userId;
    private Long totalPrice;
    private OrderStatus status;
}
