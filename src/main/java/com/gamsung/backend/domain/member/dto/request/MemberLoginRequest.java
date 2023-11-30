package com.gamsung.backend.domain.member.dto.request;

import com.gamsung.backend.domain.member.controller.request.MemberControllerLoginRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MemberLoginRequest(
        @NotEmpty(message = "데이터 형식이 올바르지 않습니다.")
        String email,
        @NotEmpty(message = "데이터 형식이 올바르지 않습니다.")
        String password
) {
    public static MemberLoginRequest from(MemberControllerLoginRequest controllerLoginRequest) {
        return new MemberLoginRequest(
                controllerLoginRequest.email(),
                controllerLoginRequest.password()
        );
    }
}
