package com.gamsung.backend.global.jwt.repository;

import com.gamsung.backend.global.jwt.entity.JwtBlackListRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
public class JwtBlackListRedisRepository implements RedisRepository<JwtBlackListRedisEntity> {

    private final static String JWT_KEY_PREFIX = "jwt:black:";

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(JwtBlackListRedisEntity entity) {
        String key = JWT_KEY_PREFIX + entity.getAccessToken();
        String value = entity.getStatus();
        long expire = entity.getExpiration();
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.MILLISECONDS);
    }

    @Override
    public Optional<String> findByKey(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(JWT_KEY_PREFIX + key));
    }

    @Override
    public Long getExpire(String key) {
        Long expireTime = redisTemplate.getExpire(JWT_KEY_PREFIX + key);
        if (expireTime == null) {
            return -1L;
        }
        else {
            return Objects.requireNonNull(redisTemplate.getExpire(JWT_KEY_PREFIX + key)) * 1000;
        }
    }

    @Override
    public Boolean deleteByKey(String key) {
        redisTemplate.expire(JWT_KEY_PREFIX + key, 1L, TimeUnit.MILLISECONDS);
        return redisTemplate.delete(JWT_KEY_PREFIX + key);
    }
}
