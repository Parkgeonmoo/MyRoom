package com.gamsung.backend.domain.member.service;

import com.gamsung.backend.domain.member.dto.request.MemberLoginRequest;
import com.gamsung.backend.domain.member.dto.request.MemberRegisterRequest;
import com.gamsung.backend.domain.member.dto.response.MemberLoginResponse;
import com.gamsung.backend.domain.member.dto.response.MemberLogoutResponse;
import com.gamsung.backend.domain.member.dto.response.MemberRegisterEmailCheckResponse;
import com.gamsung.backend.domain.member.dto.response.MemberRegisterResponse;
import com.gamsung.backend.domain.member.entity.Member;
import com.gamsung.backend.domain.member.repository.MemberRepository;
import com.gamsung.backend.global.factory.MemberTestFactory;
import com.gamsung.backend.global.jwt.JwtPair;
import com.gamsung.backend.global.jwt.dto.JwtPayload;
import com.gamsung.backend.global.jwt.service.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @DisplayName("멤버 로그인 서비스 유닛 테스트")
    @Nested
    class LoginMemberUnitTest {

        @DisplayName("사용자는 로그인 서비스를 이용할 수 있다.")
        @Test
        public void successToLoginMember() {
            // given
            Member member = MemberTestFactory.createMemberWithRandomValues(false);
            JwtPair testToken = JwtPair.builder()
                    .accessToken("fake-access-token")
                    .refreshToken("fake-refresh-token")
                    .build();
            MemberLoginRequest memberLoginRequest = new MemberLoginRequest(
                    member.getEmail(), member.getPassword());

            given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));
            given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
            given(jwtService.createTokenPair(any(JwtPayload.class))).willReturn(testToken);

            // when
            MemberLoginResponse memberLoginResponse = memberService.login(memberLoginRequest);

            // then
            Assertions.assertNotNull(memberLoginResponse.accessToken());
            Assertions.assertNotNull(memberLoginResponse.refreshToken());
        }
    }

    @DisplayName("멤버 회원가입 서비스 유닛 테스트")
    @Nested
    class RegisterMemberUnitTest {

        @DisplayName("사용자는 회원가입 서비스를 이용할 수 있다.")
        @Test
        public void successToRegisterMember() {
            // given
            Member member = MemberTestFactory.createMemberWithRandomValues(false);
            MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest(
                    member.getEmail(), member.getPassword(), member.getName());

            given(memberRepository.save(any(Member.class))).willReturn(member);
            given(passwordEncoder.encode(anyString())).willReturn(anyString());

            // when
            MemberRegisterResponse memberRegisterResponse = memberService.register(memberRegisterRequest);

            // then
            Assertions.assertEquals(memberRegisterResponse.message(), "회원가입에 성공했습니다.");
        }

        @DisplayName("사용자는 회원가입 이메일 체크 서비스를 이용할 수 있다.")
        @Test
        public void successToRegisterMemberEmailCheck() {
            // given
            Member member = MemberTestFactory.createMemberWithRandomValues(false);
            given(memberRepository.existsByEmail(anyString())).willReturn(false);

            // when
            MemberRegisterEmailCheckResponse memberRegisterEmailCheckResponse
                    = memberService.emailCheck(member.getEmail());

            // then
            Assertions.assertEquals(memberRegisterEmailCheckResponse.message(), "회원가입이 가능한 이메일입니다.");
        }
    }

    @DisplayName("멤버 로그아웃 서비스 유닛 테스트")
    @Nested
    class LogoutMemberUnitTest {

        @DisplayName("사용자는 로그아웃 서비스를 이용할 수 있다.")
        @Test
        public void successToLogoutMember() {
            // given
            Member member = MemberTestFactory.createMemberWithRandomValues(false);
            JwtPair testToken = JwtPair.builder()
                    .accessToken("fake-access-token")
                    .refreshToken("fake-refresh-token")
                    .build();

            doNothing().when(jwtService).deleteRefreshTokenAndAddAccessTokenToBlackList(anyString(), anyString());

            // when
            MemberLogoutResponse memberLogoutResponse = memberService.logout(member.getEmail(), testToken.getAccessToken());

            // then
            Assertions.assertEquals(memberLogoutResponse.message(), "로그아웃에 성공했습니다.");
        }
    }
}