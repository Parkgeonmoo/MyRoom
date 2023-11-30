package com.gamsung.backend.global.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    private ErrorCode errorCode;

    public BaseException() {
    }

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getCode() {
        return errorCode.getCode();
    }

}
