package com.gamsung.backend.domain.order.exception;

import com.gamsung.backend.domain.order.dto.response.SoldOutErrorResponse;
import com.gamsung.backend.global.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandler {
    @ExceptionHandler(OrderSoldOutException.class)
    public ResponseEntity<ApiResponse<SoldOutErrorResponse>> handleOrderSoldOutException(OrderSoldOutException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.create(Integer.parseInt(e.getCode()),
                        SoldOutErrorResponse.create(e.getMessage(), e.getSoldOutOrders())
                )
        );
    }
}
