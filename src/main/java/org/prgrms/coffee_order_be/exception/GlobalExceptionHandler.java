package org.prgrms.coffee_order_be.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErrorResponse<Object>> handleCustomException(ErrorException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse<Object> response = new ErrorResponse<>(errorCode);

        return ResponseEntity.status(HttpStatusCode.valueOf(errorCode.getCode())).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex){
        ErrorResponse<Object> response = new ErrorResponse<>(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}

