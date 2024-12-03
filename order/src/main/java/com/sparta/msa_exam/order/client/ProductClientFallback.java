package com.sparta.msa_exam.order.client;

import com.sparta.msa_exam.order.client.dto.GetProductInfoResponseDto;
import com.sparta.msa_exam.order.common.exception.CustomException;
import com.sparta.msa_exam.order.common.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient{
    @Override
    public GetProductInfoResponseDto getProductInfo(Long productId) {
        System.err.println(productId);
        throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
    }

    @Override
    public String reduceProductQuantity(Long productId, long reduceQuantity) {
        throw new CustomException(ErrorCode.INSUFFICIENT_QUANTITY);
    }

}
