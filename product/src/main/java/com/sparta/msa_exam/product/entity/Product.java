package com.sparta.msa_exam.product.entity;

import com.sparta.msa_exam.product.common.exception.CustomException;
import com.sparta.msa_exam.product.common.exception.ErrorCode;
import com.sparta.msa_exam.product.vo.Price;
import com.sparta.msa_exam.product.vo.Quantity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Price price;

    @Embedded
    private Quantity quantity;

    @Builder
    public Product(String name, long price, long quantity) {
        validateName(name);
        this.name = name;
        this.price = Price.of(price);
        this.quantity = Quantity.of(quantity);
    }

    public void addQuantity(long addQuantity) {
        this.quantity = this.quantity.addQuantity(addQuantity);
    }

    public void reduceQuantity(long reduceQuantity) {
        this.quantity = this.quantity.reduceQuantity(reduceQuantity);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_USERNAME);
        }
    }
}
