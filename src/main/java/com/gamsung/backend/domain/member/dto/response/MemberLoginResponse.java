package com.gamsung.backend.domain.member.dto.response;


import com.gamsung.backend.global.jwt.JwtPair;

public record MemberLoginResponse(
        String accessToken,
        String refreshToken,
        String name,
        String email
) {
    public static MemberLoginResponse from(JwtPair jwtPair, String name, String email) {
        return new MemberLoginResponse(jwtPair.getAccessToken(), jwtPair.getRefreshToken(), name, email);
    }
}
