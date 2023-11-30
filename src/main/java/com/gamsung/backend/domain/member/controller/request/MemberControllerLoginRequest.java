package com.gamsung.backend.domain.member.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberControllerLoginRequest(
        @NotBlank(message = "이메일을 입력해야 합니다.")
        @Email(message = "아이디가 이메일 형식에 맞지 않습니다.", regexp = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}
