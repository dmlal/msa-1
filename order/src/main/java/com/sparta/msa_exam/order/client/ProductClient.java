package com.sparta.msa_exam.order.client;

import com.sparta.msa_exam.order.client.dto.GetProductInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/products/{productId}")
    GetProductInfoResponseDto getProductInfo(@PathVariable Long productId);

}

