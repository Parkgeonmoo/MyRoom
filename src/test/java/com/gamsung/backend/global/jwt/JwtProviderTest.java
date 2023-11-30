package com.gamsung.backend.global.jwt;

import com.gamsung.backend.global.common.BaseIntegrationTest;
import com.gamsung.backend.global.jwt.dto.JwtPayload;
import com.gamsung.backend.global.jwt.exception.JwtExpiredAccessTokenException;
import com.gamsung.backend.global.jwt.exception.JwtInvalidAccessTokenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.Date;

class JwtProviderTest extends BaseIntegrationTest {

    private static final String TEST_EMAIL = "test@test.com";

    @Autowired
    private JwtProvider jwtProvider;

    @Value("${service.jwt.access-expiration}")
    private long accessTokenExpiration;

    @DisplayName("Jwt 토큰 발급")
    @Nested
    class CreateJwtToken {
        @DisplayName("createToken 호출에 성공한다.")
        @Test
        void successToCreateJwtToken() {
            // given
            Date issuedAt = Date.from(Instant.now());
            JwtPayload jwtPayload = JwtPayload.builder()
                    .email(TEST_EMAIL)
                    .issuedAt(issuedAt)
                    .build();

            // when
            String jwtToken = jwtProvider.createToken(jwtPayload, accessTokenExpiration);

            // then
            JwtPayload verifiedJwtPayload = jwtProvider.verifyAccessToken(jwtToken);
            Assertions.assertEquals(TEST_EMAIL, verifiedJwtPayload.getEmail());
            Assertions.assertEquals(issuedAt.getTime() / 1000, verifiedJwtPayload.getIssuedAt().getTime() / 1000);
        }
    }

    @DisplayName("Jwt 토큰 검증 실패")
    @Nested
    class VerifyJwtToken {

        private static final String WRONG_SECRET_KEY = "Zjk1MDQzY2RhNmE5MTc1ZjA0ZDQwYjhjNTQ1YWQyMjZiZTEwOTQ2N2ExNDk1NWQ1Y2Q0NGI5ZmQyNzMyZjgyNg==";

        @DisplayName("만료된 토큰을 검증할 때 JwtTokenExpiredException 발생")
        @Test
        void failToVerifyJwtTokenWhenTokenExpired() {
            // given
            Date issuedAt = new Date(System.currentTimeMillis());
            long shortExpirationTime = 1;
            JwtPayload jwtPayload = JwtPayload.builder()
                    .email(TEST_EMAIL)
                    .issuedAt(issuedAt)
                    .build();

            // when
            String jwtToken = jwtProvider.createToken(jwtPayload, shortExpirationTime);

            // then
            Assertions.assertThrows(JwtExpiredAccessTokenException.class, () -> jwtProvider.verifyAccessToken(jwtToken));
        }

        @DisplayName("다른 Secret Key로 검증할 때 JwtInvalidTokenException 발생")
        @Test
        void failToVerifyJwtTokenWhenWrongSecretKey() {
            // given
            Date issuedAt = new Date(System.currentTimeMillis());
            JwtPayload jwtPayload = JwtPayload.builder()
                    .email(TEST_EMAIL)
                    .issuedAt(issuedAt)
                    .build();
            JwtProvider otherJwtProvider = new JwtProvider(WRONG_SECRET_KEY);

            // when
            String wrongToken = otherJwtProvider.createToken(jwtPayload, accessTokenExpiration);

            // then
            Assertions.assertThrows(JwtInvalidAccessTokenException.class, () -> jwtProvider.verifyAccessToken(wrongToken));
        }
    }
}