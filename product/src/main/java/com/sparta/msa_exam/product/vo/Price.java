package com.sparta.msa_exam.product.vo;

import com.sparta.msa_exam.product.common.exception.CustomException;
import com.sparta.msa_exam.product.common.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Price {

    @Column(name = "price", nullable = false)
    private long price;

    public static Price of(long price) {
        if (price < 0) {
            throw new CustomException(ErrorCode.INVALID_PRICE_NOT_BELOW_ZERO);
        }
        return new Price(price);
    }
}
