package com.sparta.msa_exam.order.client;

import com.sparta.msa_exam.order.client.dto.GetProductInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "product",
        fallback = ProductClientFallback.class)
@Primary
public interface ProductClient {

    @GetMapping("/products/{productId}")
    GetProductInfoResponseDto getProductInfo(@PathVariable Long productId);

    @PutMapping("/products/{productId}/quantity/decrease")
    String reduceProductQuantity(@PathVariable Long productId,
                                 @RequestParam long reduceQuantity);
}

