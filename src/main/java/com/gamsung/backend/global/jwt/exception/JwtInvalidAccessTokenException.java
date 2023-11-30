package com.gamsung.backend.global.jwt.exception;

import com.gamsung.backend.global.exception.ErrorCode;
import com.gamsung.backend.global.exception.UnAuthException;

public class JwtInvalidAccessTokenException extends UnAuthException {
    public JwtInvalidAccessTokenException() {
        super(ErrorCode.JWT_INVALID_ACCESS_TOKEN);
    }
}
