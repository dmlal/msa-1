package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.client.AuthClient;
import com.sparta.msa_exam.product.common.exception.CustomException;
import com.sparta.msa_exam.product.common.exception.ErrorCode;
import com.sparta.msa_exam.product.controller.dto.AddProductRequestDto;
import com.sparta.msa_exam.product.controller.dto.AddProductResponseDto;
import com.sparta.msa_exam.product.controller.dto.UpdateProductRequestDto;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AuthClient authClient;

    public AddProductResponseDto addProduct(AddProductRequestDto requestDto, String token) {
        checkRoleAdmin(token);

        Product product = Product.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .quantity(requestDto.getQuantity())
                .build();

        Product newProduct = productRepository.save(product);
        return new AddProductResponseDto(newProduct.getId());
    }

    @Transactional
    public String updateProductInfo(Long productId, String token, UpdateProductRequestDto requestDto) {
        checkRoleAdmin(token);

        Product product = getProduct(productId);

        product.updateProduct(requestDto);

        return "수정 완료";
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private void checkRoleAdmin(String token) {
        Boolean isAdmin = authClient.validateAdminToken(token);
        if (!isAdmin) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }
}
