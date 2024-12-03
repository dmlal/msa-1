package com.sparta.msa_exam.product.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDto {

    private Long productId;
    private long quantity;

}