package org.prgrms.coffee_order_be.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.prgrms.coffee_order_be.exception.ErrorCode.SUCCESS;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ErrorResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    private String message;

    private int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청 성공
    public ErrorResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
    }

    // 요청 실패
    public ErrorResponse(ErrorCode errors) {
        this.isSuccess = errors.isSuccess();
        this.message = errors.getMessage();
        this.code = errors.getCode();
    }

}