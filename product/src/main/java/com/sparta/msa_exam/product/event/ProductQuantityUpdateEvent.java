package com.sparta.msa_exam.product.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityUpdateEvent {

    private Long productId;
    private Long quantity;

}