package com.sparta.msa_exam.order.event;

import com.sparta.msa_exam.order.controller.dto.OrderProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelledEvent implements Serializable {

    private Long orderId;
    private List<OrderProductDto> orderProductDtoList;

}
