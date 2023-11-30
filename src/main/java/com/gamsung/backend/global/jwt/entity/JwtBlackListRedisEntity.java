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
public class JwtBlackListRedisEntity {
    @Id
    private String accessToken;
    private String status;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    @Builder
    private JwtBlackListRedisEntity(String accessToken, String status, long expiration) {
        this.accessToken = accessToken;
        this.status = status;
        this.expiration = expiration;
    }
}
