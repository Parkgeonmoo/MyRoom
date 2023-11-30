package com.gamsung.backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamsung.backend.domain.cart.dto.request.CartEntryRequest;
import com.gamsung.backend.domain.member.controller.request.MemberControllerLoginRequest;
import com.gamsung.backend.domain.member.entity.Member;
import com.gamsung.backend.domain.member.repository.MemberRepository;
import com.gamsung.backend.domain.member.service.MemberService;
import com.gamsung.backend.global.common.BaseRedisContainerTest;
import com.gamsung.backend.global.factory.MemberTestFactory;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartIntegrationTest extends BaseRedisContainerTest {

    private final static String BASE_URL = "/v1/member";
    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String token;

    @DisplayName("로그인한 멤버가 장바구니에 상품을 추가할 때")
    @Nested
    class MemberCartTest {

        @DisplayName("로그인한 멤버는 장바구니에 상품을 추가할 수 있다")
        @Test
        public void successToEntryMyCartWhenLoggedInMemberTest() throws Exception {
            // given
            Member member = MemberTestFactory.createMemberWithRandomValues(false);
            memberRepository.save(Member.builder()
                    .email(member.getEmail())
                    .password(passwordEncoder.encode(member.getPassword()))
                    .name(member.getName())
                    .build());
            MemberControllerLoginRequest loginRequest = new MemberControllerLoginRequest(
                    member.getEmail(), member.getPassword());

            // 로그인
            MvcResult loginResult = mockMvc.perform(post(BASE_URL + "/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andReturn();
            String response = loginResult.getResponse().getContentAsString();
            String accessToken = JsonPath.parse(response).read("$.data.access_token");

            // 장바구니 요청 데이터 생성
            CartEntryRequest cartEntryRequest = new CartEntryRequest(
                    1L, "Test Name", "Test Address", LocalDate.now(), LocalDate.now().plusDays(1), 2L, 10000L, "Test Image");

            // when
            ResultActions cartAction = mockMvc.perform(post("/v1/cart")
                    .header("Authorization", JWT_TOKEN_PREFIX + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cartEntryRequest)));

            // then
            cartAction
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(4001))
                    .andExpect(jsonPath("$.data").exists())
                    .andDo(print());
        }
    }


}
