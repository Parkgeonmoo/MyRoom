package com.gamsung.backend.domain.member.dto.request;

import com.gamsung.backend.domain.member.controller.request.MemberControllerRegisterRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MemberRegisterRequest(
        @NotEmpty(message = "데이터 형식이 올바르지 않습니다.")
        String email,
        @NotEmpty(message = "데이터 형식이 올바르지 않습니다.")
        String password,
        @NotEmpty(message = "데이터 형식이 올바르지 않습니다.")
        String name
) {
    public static MemberRegisterRequest from(MemberControllerRegisterRequest controllerRegisterRequest) {
        return new MemberRegisterRequest(
                controllerRegisterRequest.email(),
                controllerRegisterRequest.password(),
                controllerRegisterRequest.name()
        );
    }
}
