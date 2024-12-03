package com.sparta.msa_exam.order.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*  400 BAD_REQUEST : 잘못된 요청  */
    EXPIRED_TOKEN(400, "만료된 토큰입니다."),
    INVALID_PRICE_NOT_BELOW_ZERO(400, "유효하지않은 가격입니다.  가격은 0원 아래가 될 수 없습니다."),
    INVALID_QUANTITY_NOT_BELOW_ZERO(400, "유효하지않은 수량입니다. 수량은 마이너스가 될 수 없습니다."),
    INSUFFICIENT_QUANTITY(400, "재고가 부족합니다."),
    INVALID_USERNAME(400, "유저네임은 필수입니다."),
    CAN_NOT_ADD_PRODUCT(400, "주문 확정 이후에는 상품을 추가할 수 없습니다."),

    /*  401 UNAUTHORIZED : 인증 안됨  */
    UNAUTHORIZED(401, "인증되지 않았습니다."),

    /*  403 FORBIDDEN : 권한 없음  */
    FORBIDDEN(403, "권한이 없습니다."),

    /*  404 NOT_FOUND : Resource 권한 없음, Resource 를 찾을 수 없음  */
    ACCESS_DENIED(404, "접근 권한이 없습니다."),
    USER_NOT_FOUND(404, "유저를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(404, "주문을 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(404, "상품을 찾을 수 없습니다."),

    /*  408 REQUEST_TIMEOUT : 요청에 대한 응답 시간 초과  */
    TIMEOUT_ERROR(408, "응답시간을 초과하였습니다."),

    /*  409 CONFLICT : Resource 중복  */
    ALREADY_EXIST_USERID(409, "이미 존재하는 USERID 입니다."),

    /*  500 INTERNAL_SERVER_ERROR : 서버 에러  */
    INTERNAL_SERVER_ERROR(500, "내부 서버 에러입니다."),
    PRODUCT_SERVER_ERROR(500, "PRODUCT 서버 에러입니다."),
    INTERRUPTED_ERROR(500, " Interrupted 에러 발생."),

    /*  502 BAD_GATEWAY  연결 실패   */
    ;

    private final Integer httpStatus;
    private final String message;
}
