package com.gamsung.backend.domain.cart.exception;

import com.gamsung.backend.global.exception.BaseException;
import com.gamsung.backend.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CartException extends BaseException {

    public CartException() {
       super();
    }
    public CartException(ErrorCode errorCode) {
        super(errorCode);
    }

}
