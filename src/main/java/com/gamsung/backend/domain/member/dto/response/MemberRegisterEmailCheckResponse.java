package com.gamsung.backend.domain.member.dto.response;


public record MemberRegisterEmailCheckResponse(
        String message
) {
    public static MemberRegisterEmailCheckResponse create() {
        return new MemberRegisterEmailCheckResponse("회원가입이 가능한 이메일입니다.");
    }
}
