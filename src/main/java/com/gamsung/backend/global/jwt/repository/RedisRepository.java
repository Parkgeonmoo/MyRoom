package com.gamsung.backend.global.jwt.repository;

import java.util.Optional;

public interface RedisRepository<T> {
    void save(T entity);

    Optional<String> findByKey(String key);

    Long getExpire(String key);

    Boolean deleteByKey(String key);
}
