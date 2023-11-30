package com.gamsung.backend.domain.member.dto.response;


public record MemberLogoutResponse(
        String message
) {
    public static MemberLogoutResponse create() {
        return new MemberLogoutResponse("로그아웃에 성공했습니다.");
    }
}
