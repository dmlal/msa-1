package com.sparta.msa_exam.product.controller;


import com.sparta.msa_exam.product.controller.dto.AddProductRequestDto;
import com.sparta.msa_exam.product.controller.dto.AddProductResponseDto;
import com.sparta.msa_exam.product.controller.dto.GetProductInfoResponseDto;
import com.sparta.msa_exam.product.controller.dto.UpdateProductRequestDto;
import com.sparta.msa_exam.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 정보 추가
     * admin 권한 필요
     */
    @PostMapping("/admin")
    public ResponseEntity<AddProductResponseDto> addProduct(
            @RequestBody AddProductRequestDto requestDto,
            @RequestHeader("Authorization") String token
    ) {
        AddProductResponseDto responseDto = productService.addProduct(requestDto, token);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * 상품 정보 수정 (이름, 가격)
     * admin 권한 필요
     * 수량은 오더에서도 자주 호출하므로 API 분리
     */
    @PatchMapping("/admin/{productId}")
    public ResponseEntity<String> updateProductInfo(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateProductRequestDto requestDto
            ) {
        return ResponseEntity.ok(productService.updateProductInfo(productId, token, requestDto));
    }

    /**
     * 상품 정보 수정 (재고 수량)
     * 모든 권한
     */
    @PatchMapping("/{productId}/quantity/increase")
    public ResponseEntity<String> addProductQuantity(
            @PathVariable Long productId,
            @RequestParam long addQuantity
    ) {
        productService.addProductQuantity(productId, addQuantity);
        return ResponseEntity.ok("수량 증가 완료");
    }

    @PatchMapping("/{productId}/quantity/decrease")
    public ResponseEntity<String> reduceProductQuantity(
            @PathVariable Long productId,
            @RequestParam long reduceQuantity
    ) {
        productService.reduceProductQuantity(productId, reduceQuantity);
        return ResponseEntity.ok("수량 감소 완료");
    }


    /**
     * 상품 정보 삭제
     * admin 권한 필요
     */
    @DeleteMapping("(/admin/{productId}")
    public ResponseEntity<Void> deleteProductInfo(

    ) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<GetProductInfoResponseDto> getProductInfo(
            @PathVariable Long productId
    ){
        GetProductInfoResponseDto responseDto = new GetProductInfoResponseDto(1L, "dd", 33,1);
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
