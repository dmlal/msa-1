package com.sparta.msa_exam.order.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDto {

    private List<Long> productIdList;

}
