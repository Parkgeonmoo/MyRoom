package com.gamsung.backend.global.jwt.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtPayload {
    private String id;
    private String email;
    private Date issuedAt;

    @Builder
    private JwtPayload(String id, String email, Date issuedAt) {
        this.id = id;
        this.email = email;
        this.issuedAt = issuedAt;
    }

    public static JwtPayload from(Long id, String email) {
        return JwtPayload.builder()
                .id(String.valueOf(id))
                .email(email)
                .issuedAt(Date.from(Instant.now()))
                .build();
    }
}
