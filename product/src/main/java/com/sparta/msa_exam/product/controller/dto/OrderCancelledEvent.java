package com.sparta.msa_exam.product.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelledEvent {

    private Long orderId;
    private List<OrderProductDto> orderProductDtoList;
}
