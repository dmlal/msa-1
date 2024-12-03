package com.sparta.msa_exam.order.event;

import com.sparta.msa_exam.order.controller.dto.OrderProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateEvent implements Serializable {
    private Long orderId;
    private Long userId;
    private List<OrderProductDto> orderProductDtoList;
}
