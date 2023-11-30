package com.gamsung.backend.global.jwt.util;

import org.springframework.util.StringUtils;

public class JwtUtil {
    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    public static String extractAccessToken(String authHeaderValue) {
        if (StringUtils.hasText(authHeaderValue) && authHeaderValue.startsWith(JWT_TOKEN_PREFIX)) {
            return authHeaderValue.substring(JWT_TOKEN_PREFIX.length());
        }
        return "";
    }

}
