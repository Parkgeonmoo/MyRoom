package com.gamsung.backend.global.jwt.service;

import com.gamsung.backend.global.jwt.JwtPair;
import com.gamsung.backend.global.jwt.JwtProvider;
import com.gamsung.backend.global.jwt.controller.request.RefreshAccessTokenRequest;
import com.gamsung.backend.global.jwt.dto.JwtPayload;
import com.gamsung.backend.global.jwt.entity.JwtBlackListRedisEntity;
import com.gamsung.backend.global.jwt.entity.JwtRedisEntity;
import com.gamsung.backend.global.jwt.exception.JwtExpiredRefreshTokenException;
import com.gamsung.backend.global.jwt.exception.JwtInvalidAccessTokenException;
import com.gamsung.backend.global.jwt.exception.JwtInvalidRefreshTokenException;
import com.gamsung.backend.global.jwt.repository.JwtBlackListRedisRepository;
import com.gamsung.backend.global.jwt.repository.JwtRefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtRefreshTokenRedisRepository jwtRefreshTokenRedisRepository;
    private final JwtBlackListRedisRepository jwtBlackListRedisRepository;
    private final JwtProvider jwtProvider;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    @Value("${service.jwt.refresh-expiration}")
    private Long refreshExpiration;

    public JwtPair createTokenPair(JwtPayload jwtPayload) {
        String accessToken = jwtProvider.createToken(jwtPayload, accessExpiration);
        String refreshToken = jwtProvider.createToken(jwtPayload, refreshExpiration);

        jwtRefreshTokenRedisRepository.save(JwtRedisEntity.builder()
                .memberEmail(jwtPayload.getEmail())
                .refreshToken(refreshToken)
                .expiration(refreshExpiration)
                .build());

        return JwtPair.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtPayload verifyAccessToken(String jwtAccessToken) {
        JwtPayload jwtPayload = jwtProvider.getExpiredTokenPayload(jwtAccessToken);
        if (jwtPayload == null) {
            jwtPayload = jwtProvider.verifyAccessToken(jwtAccessToken);
        }
        if (jwtBlackListRedisRepository.findByKey(jwtAccessToken).isPresent()) {
            throw new JwtInvalidAccessTokenException();
        }
        return jwtPayload;
    }

    public JwtPayload verifyRefreshToken(String jwtToken) {
        return jwtProvider.verifyRefreshToken(jwtToken);
    }

    public JwtPair refreshAccessToken(RefreshAccessTokenRequest request, String email, String originAccessToken) {
        JwtPayload jwtPayload = verifyRefreshToken(request.refreshToken());

        Optional<String> storedRefreshToken = jwtRefreshTokenRedisRepository.findByKey(jwtPayload.getEmail());

        if (storedRefreshToken.isEmpty()) {
            throw new JwtExpiredRefreshTokenException();
        }

        if (!request.refreshToken().equals(storedRefreshToken.get())) {
            throw new JwtInvalidRefreshTokenException();
        }

        JwtPayload newJwtPayload = JwtPayload.from(Long.parseLong(jwtPayload.getId()), jwtPayload.getEmail());
        String newAccessToken = jwtProvider.createToken(newJwtPayload, accessExpiration);

        addAccessTokenToBlackList(email, originAccessToken);

        return JwtPair.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.refreshToken())
                .build();
    }

    public void deleteRefreshTokenAndAddAccessTokenToBlackList(String email, String accessToken) {
        addAccessTokenToBlackList(email, accessToken);
        jwtRefreshTokenRedisRepository.deleteByKey(email);
    }

    private void addAccessTokenToBlackList(String email, String accessToken) {
        long refreshTokenExpiredTime = jwtRefreshTokenRedisRepository.getExpire(email);

        jwtRefreshTokenRedisRepository.deleteByKey(email);
        jwtBlackListRedisRepository.save(JwtBlackListRedisEntity.builder()
                .accessToken(accessToken)
                .status("black")
                .expiration(refreshTokenExpiredTime)
                .build()
        );
    }
}
