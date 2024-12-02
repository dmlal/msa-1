package com.sparta.msa_exam.order.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetProductInfoResponseDto {

    private final Long id;
    private final String name;
    private final long price;
    private final long quantity;

}