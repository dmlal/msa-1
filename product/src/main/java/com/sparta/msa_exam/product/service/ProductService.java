package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.client.AuthClient;
import com.sparta.msa_exam.product.common.exception.CustomException;
import com.sparta.msa_exam.product.common.exception.ErrorCode;
import com.sparta.msa_exam.product.controller.dto.AddProductRequestDto;
import com.sparta.msa_exam.product.controller.dto.AddProductResponseDto;
import com.sparta.msa_exam.product.controller.dto.GetProductInfoResponseDto;
import com.sparta.msa_exam.product.controller.dto.UpdateProductRequestDto;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AuthClient authClient;

    @CacheEvict(value = "productListCache", allEntries = true)
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

    @Transactional
    public void addProductQuantity(Long productId, long addQuantity) {
        Product product = getProduct(productId);

        product.addQuantity(addQuantity);
    }

    @Transactional
    public void reduceProductQuantity(Long productId, long reduceQuantity) {
        Product product = getProduct(productId);

        product.reduceQuantity(reduceQuantity);
    }

    public GetProductInfoResponseDto getProductInfo(Long productId) {
        Product product = getProduct(productId);
        return new GetProductInfoResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice().getPrice(),
                product.getQuantity().getQuantity()
        );
    }

    @Transactional
    public void deleteProductInfo(Long productId, String token) {
        checkRoleAdmin(token);

        Product product = getProduct(productId);

        productRepository.delete(product);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Cacheable(cacheNames = "productListCache", unless = "#result == null || #result.isEmpty()")
    public List<GetProductInfoResponseDto> getProductList() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(product -> new GetProductInfoResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice().getPrice(),
                        product.getQuantity().getQuantity()
                )).toList();
    }

    private void checkRoleAdmin(String token) {
        Boolean isAdmin = authClient.validateAdminToken(token);
        if (!isAdmin) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }
}
