package com.sparta.msa_exam.order.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetProductInfoResponseDto {

    private Long id;
    private String name;
    private long price;
    private long quantity;

}