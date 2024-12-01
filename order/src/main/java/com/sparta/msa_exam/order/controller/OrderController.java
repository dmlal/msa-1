package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.controller.dto.OrderRequestDto;
import com.sparta.msa_exam.order.controller.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            // authen
            OrderRequestDto requestDto
    ){
        OrderResponseDto responseDto = new OrderResponseDto();
        return ResponseEntity.ok(responseDto);
    }
}
