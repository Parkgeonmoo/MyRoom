package com.gamsung.backend.global.jwt.controller.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RefreshAccessTokenRequest(
        @NotBlank(message = "리프레시 토큰을 입력해야 합니다.")
        String refreshToken
) {
}
