package com.sparta.msa_exam.product.controller;


import com.sparta.msa_exam.product.controller.dto.AddProductRequestDto;
import com.sparta.msa_exam.product.controller.dto.AddProductResponseDto;
import com.sparta.msa_exam.product.controller.dto.GetProductInfoResponseDto;
import com.sparta.msa_exam.product.controller.dto.UpdateProductRequestDto;
import com.sparta.msa_exam.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/admin")
    public ResponseEntity<AddProductResponseDto> addProduct(
            @RequestBody AddProductRequestDto requestDto,
            @RequestHeader("Authorization") String token
    ) {
        AddProductResponseDto responseDto = productService.addProduct(requestDto, token);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/admin/{productId}")
    public ResponseEntity<String> updateProductInfo(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateProductRequestDto requestDto
            ) {
        return ResponseEntity.ok(productService.updateProductInfo(productId, token, requestDto));
    }

    @PutMapping("/{productId}/quantity/increase")
    public ResponseEntity<String> addProductQuantity(
            @PathVariable Long productId,
            @RequestParam long addQuantity
    ) {
        productService.addProductQuantity(productId, addQuantity);
        return ResponseEntity.ok("수량 증가 완료");
    }

    @PutMapping("/{productId}/quantity/decrease")
    public ResponseEntity<String> reduceProductQuantity(
            @PathVariable Long productId,
            @RequestParam long reduceQuantity
    ) {
        productService.reduceProductQuantity(productId, reduceQuantity);
        return ResponseEntity.ok("수량 감소 완료");
    }

    @DeleteMapping("/admin/{productId}")
    public ResponseEntity<Void> deleteProductInfo(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String token
    ) {
        productService.deleteProductInfo(productId, token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<GetProductInfoResponseDto> getProductInfo(
            @PathVariable Long productId
    ){
        GetProductInfoResponseDto responseDto = productService.getProductInfo(productId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<GetProductInfoResponseDto>> getProductList(
    ) {
        List<GetProductInfoResponseDto> productList = productService.getProductList();
        return ResponseEntity.ok(productList);
    }
}
