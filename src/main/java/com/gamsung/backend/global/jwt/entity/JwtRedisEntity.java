package com.gamsung.backend.global.jwt.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash("jwt")
public class JwtRedisEntity {
    @Id
    private String memberEmail;
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    @Builder
    private JwtRedisEntity(String memberEmail, String refreshToken, long expiration) {
        this.memberEmail = memberEmail;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
