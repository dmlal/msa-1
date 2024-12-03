package com.sparta.msa_exam.order.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderResponseDto {

    private Long orderId;
    private Long userId;
    private long totalPrice;
    private List<Long> failedProductList;
}
