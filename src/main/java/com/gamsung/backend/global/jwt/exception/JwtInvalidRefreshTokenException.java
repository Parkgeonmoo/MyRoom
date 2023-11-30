package com.gamsung.backend.global.jwt.exception;

import com.gamsung.backend.global.exception.ErrorCode;
import com.gamsung.backend.global.exception.ForbiddenException;

public class JwtInvalidRefreshTokenException extends ForbiddenException {
    public JwtInvalidRefreshTokenException() {
        super(ErrorCode.JWT_INVALID_REFRESH_TOKEN);
    }
}
