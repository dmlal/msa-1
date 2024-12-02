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
public class Quantity {

    @Column(name = "quantity", nullable = false)
    private long quantity;

    public static Quantity of(long quantity) {
        if (quantity < 0) {
            throw new CustomException(ErrorCode.INVALID_QUANTITY_NOT_BELOW_ZERO);
        }
        return new Quantity(quantity);
    }

    public Quantity addQuantity(long addQuantity) {
        return Quantity.of(this.quantity + addQuantity);
    }

    public Quantity reduceQuantity(long reduceQuantity) {
        if (this.quantity < reduceQuantity) {
            throw new CustomException(ErrorCode.INSUFFICIENT_QUANTITY);
        }
        return Quantity.of(this.quantity - reduceQuantity);
    }
}
