package com.gamsung.backend.global.jwt.exception;

import com.gamsung.backend.global.exception.ErrorCode;
import com.gamsung.backend.global.exception.ForbiddenException;

public class JwtExpiredRefreshTokenException extends ForbiddenException {
    public JwtExpiredRefreshTokenException() {
        super(ErrorCode.JWT_EXPIRED_REFRESH_TOKEN);
    }
}
