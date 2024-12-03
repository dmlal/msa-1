package com.sparta.msa_exam.order.vo;

import com.sparta.msa_exam.order.common.exception.CustomException;
import com.sparta.msa_exam.order.common.exception.ErrorCode;
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

    private long price;

    public static Price of(long price) {
        if (price < 0) {
            throw new CustomException(ErrorCode.INVALID_PRICE_NOT_BELOW_ZERO);
        }
        return new Price(price);
    }
}
