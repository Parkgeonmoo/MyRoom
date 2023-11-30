package com.gamsung.backend.global.jwt.exception;

import com.gamsung.backend.global.exception.ErrorCode;
import com.gamsung.backend.global.exception.UnAuthException;

public class JwtExpiredAccessTokenException extends UnAuthException {
    public JwtExpiredAccessTokenException() {
        super(ErrorCode.JWT_EXPIRED_ACCESS_TOKEN);
    }
}
