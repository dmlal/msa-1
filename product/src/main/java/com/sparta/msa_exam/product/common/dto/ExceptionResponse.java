package com.sparta.msa_exam.product.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExceptionResponse<T> {

    private final Integer status;
    private final String message;
    private final T data;

    public static <T> ExceptionResponse<T> of(Integer status, String message, T data) {
        return new ExceptionResponse<>(status, message, data);
    }
}
