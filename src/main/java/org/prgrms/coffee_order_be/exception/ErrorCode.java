package org.prgrms.coffee_order_be.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 1000 : 요청 성공
     */

    SUCCESS(true, 200, "요청에 성공했습니다."),

    /**
     * 400 : Bad Request
     */
    NOT_EXIST_PRODUCT(false, 400, "상품이 존재하지 않습니다."),
    NOT_EXIST_ORDER(false, 400, "주문이 존재하지 않습니다."),
    IN_PROGRESS_DELIVERY(false, 400, "배달이 진행 중입니다. 상담사에게 상담 부탁드립니다.");


    private final boolean isSuccess;

    private final int code;

    private final String message;
}
