package com.gamsung.backend.integration;

import com.gamsung.backend.domain.member.controller.request.MemberControllerLoginRequest;
import com.gamsung.backend.domain.member.dto.request.MemberLoginRequest;
import com.gamsung.backend.domain.member.dto.response.MemberLoginResponse;
import com.gamsung.backend.domain.member.entity.Member;
import com.gamsung.backend.domain.member.repository.MemberRepository;
import com.gamsung.backend.domain.member.service.MemberService;
import com.gamsung.backend.global.common.BaseRedisContainerTest;
import com.gamsung.backend.global.factory.MemberTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberIntegrationTest extends BaseRedisContainerTest {
    private final static String BASE_URL = "/v1/member";
    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("멤버가 로그인을 할 때")
    @Nested
    class MemberLoginTest {

        @DisplayName("회원가입이 되어 있는 멤버는 로그인을 할 수 있다")
        @Test
        public void successToMemberLoginWhenRegisteredMemberTest() throws Exception {
            // given
            Member member = MemberTestFactory.createMemberWithRandomValues(false);
            memberRepository.save(Member.builder()
                    .email(member.getEmail())
                    .password(passwordEncoder.encode(member.getPassword()))
                    .name(member.getName())
                    .build());
            MemberControllerLoginRequest loginRequest = new MemberControllerLoginRequest(
                    member.getEmail(), member.getPassword());

            // when
            ResultActions loginAction = mockMvc.perform(post(BASE_URL + "/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)));

            // then
            loginAction
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1000))
                    .andExpect(jsonPath("$.data").exists())
                    .andExpect(jsonPath("$.data.access_token").exists())
                    .andExpect(jsonPath("$.data.refresh_token").exists())
                    .andDo(print());
        }

        @DisplayName("회원가입이 되어 있지 않으면 로그인을 할 수 없다")
        @Test
        public void failToMemberLoginWhenNotRegisteredMemberTest() throws Exception {
            // given
            Member member = MemberTestFactory.createMemberWithRandomValues(false);
            MemberControllerLoginRequest loginRequest = new MemberControllerLoginRequest(
                    member.getEmail(), member.getPassword());

            // when
            ResultActions loginAction = mockMvc.perform(post(BASE_URL + "/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)));

            // then
            loginAction
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }
    }

    @DisplayName("멤버가 로그아웃을 할 때")
    @Nested
    class MemberLogoutTest {

        @DisplayName("로그인을 한 멤버는 로그아웃을 할 수 있다")
        @Test
        public void successToMemberLogoutWhenLoginMemberTest() throws Exception {
            // given
            Member member = MemberTestFactory.createMemberWithRandomValues(false);
            memberRepository.save(Member.builder()
                    .email(member.getEmail())
                    .password(passwordEncoder.encode(member.getPassword()))
                    .name(member.getName())
                    .build());

            MemberLoginResponse loginResponse = memberService.login(
                    new MemberLoginRequest(member.getEmail(), member.getPassword()));
            String accessToken = loginResponse.accessToken();


            // when
            ResultActions logoutAction = mockMvc.perform(post(BASE_URL + "/logout")
                    .header(HttpHeaders.AUTHORIZATION, JWT_TOKEN_PREFIX + accessToken));

            // then
            logoutAction
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(1011))
                    .andExpect(jsonPath("$.data").exists())
                    .andExpect(jsonPath("$.data.message").value("로그아웃에 성공했습니다."))
                    .andDo(print());
        }

        @DisplayName("로그인이 되어 있지 않은 멤버는 로그아웃을 할 수 없다")
        @Test
        public void failToMemberLogoutWhenNotLoginMemberTest() throws Exception {
            // given
            Member member = MemberTestFactory.createMemberWithRandomValues(false);
            memberRepository.save(Member.builder()
                    .email(member.getEmail())
                    .password(passwordEncoder.encode(member.getPassword()))
                    .name(member.getName())
                    .build());

            // when
            ResultActions logoutAction = mockMvc.perform(post(BASE_URL + "/logout"));

            // then
            logoutAction
                    .andExpect(status().isForbidden())
                    .andDo(print());
        }
    }
}
