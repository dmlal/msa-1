package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.controller.dto.CreateOrderRequestDto;
import com.sparta.msa_exam.order.controller.dto.CreateOrderResponseDto;
import com.sparta.msa_exam.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> createOrder(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CreateOrderRequestDto requestDto
    ){
        CreateOrderResponseDto responseDto = orderService.createOrder(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
