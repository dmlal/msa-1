package com.sparta.msa_exam.product.controller;


import com.sparta.msa_exam.product.controller.dto.GetProductInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    /**
     * 상품 정보 추가
     * admin 권한 필요
     */
    @PostMapping
    public ResponseEntity<String> addProduct(
            // @Authen
    ){

        return ResponseEntity.ok("dd");
    }

    /**
     * 상품 정보 수정
     * admin 권한 필요
     */
//    @PatchMapping("/{productId}")
//    public ResponseEntity<String> updateProductInfo(
//
//    ){
//
//    }

    /**
     * 상품 정보 삭제
     * admin 권한 필요
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductInfo(

    ) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<GetProductInfoResponseDto> getProductInfo(
            @PathVariable Long productId
    ){
        GetProductInfoResponseDto responseDto = new GetProductInfoResponseDto(1L, "dd", 33);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<GetProductInfoResponseDto>> getProductList(
            // 페이저블
    ){
//        List<GetProductInfoResponseDto> productList = productService.getProductList();
//        return ResponseEntity.ok(productList);
        return ResponseEntity.ok(new ArrayList<>());
    }
}
