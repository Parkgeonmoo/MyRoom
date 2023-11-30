package com.gamsung.backend.domain.member.dto.response;


public record MemberRegisterResponse(
        String message
) {
    public static MemberRegisterResponse create() {
        return new MemberRegisterResponse("회원가입에 성공했습니다.");
    }
}
