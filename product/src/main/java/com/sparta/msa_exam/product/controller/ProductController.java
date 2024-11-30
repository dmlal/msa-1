package com.sparta.msa_exam.product.controller;


import com.sparta.msa_exam.product.controller.dto.GetProductInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    @PostMapping("/{productId}")
    public ResponseEntity<String> addProduct(
            // @Authen
            @PathVariable Long productId
    ){

        return ResponseEntity.ok("dd");
    }

    @GetMapping("/{productId}")
    public ResponseEntity<GetProductInfoResponseDto> getProductInfo(
            @PathVariable Long productId
            // 페이저블
    ){
        GetProductInfoResponseDto responseDto = new GetProductInfoResponseDto(1L, "dd", 33);
        return ResponseEntity.ok(responseDto);
    }



}
