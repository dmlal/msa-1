package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.client.AuthClient;
import com.sparta.msa_exam.product.common.exception.CustomException;
import com.sparta.msa_exam.product.common.exception.ErrorCode;
import com.sparta.msa_exam.product.controller.dto.AddProductRequestDto;
import com.sparta.msa_exam.product.controller.dto.AddProductResponseDto;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.repository.ProductRepository;
import com.sparta.msa_exam.product.vo.Price;
import com.sparta.msa_exam.product.vo.Quantity;
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

    private void checkRoleAdmin(String token) {
        Boolean isAdmin = authClient.validateAdminToken(token);
        if (!isAdmin) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }
}
