package com.gamsung.backend.domain.member.controller;

import com.gamsung.backend.domain.member.controller.request.MemberControllerLoginRequest;
import com.gamsung.backend.domain.member.controller.request.MemberControllerRegisterEmailCheckRequest;
import com.gamsung.backend.domain.member.controller.request.MemberControllerRegisterRequest;
import com.gamsung.backend.domain.member.dto.request.MemberLoginRequest;
import com.gamsung.backend.domain.member.dto.request.MemberRegisterRequest;
import com.gamsung.backend.domain.member.dto.response.MemberLoginResponse;
import com.gamsung.backend.domain.member.dto.response.MemberLogoutResponse;
import com.gamsung.backend.domain.member.dto.response.MemberRegisterEmailCheckResponse;
import com.gamsung.backend.domain.member.dto.response.MemberRegisterResponse;
import com.gamsung.backend.domain.member.service.MemberService;
import com.gamsung.backend.global.common.ApiResponse;
import com.gamsung.backend.global.jwt.util.JwtUtil;
import com.gamsung.backend.global.resolver.AuthContext;
import com.gamsung.backend.global.resolver.MemberAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Tag(name = "멤버")
@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "사용자는 가입된 아이디와 비밀번호로 로그인 인증을 합니다.")
    public ResponseEntity<ApiResponse<MemberLoginResponse>> memberLogin(
            @Valid @RequestBody MemberControllerLoginRequest loginRequest
    ) {
        MemberLoginResponse response = memberService.login(MemberLoginRequest.from(loginRequest));
        return ResponseEntity.ok(ApiResponse.create(1000, response));
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입 API", description = "사용자는 로그인을 하기 전에 이메일, 비밀번호, 이름으로 회원가입을 해야 합니다.")
    public ResponseEntity<ApiResponse<MemberRegisterResponse>> memberRegister(
            @Valid @RequestBody MemberControllerRegisterRequest registerRequest
    ) {
        MemberRegisterResponse response = memberService.register(MemberRegisterRequest.from(registerRequest));
        return ResponseEntity.created(URI.create("/")).body(ApiResponse.create(1003, response));
    }

    @GetMapping("/register/check")
    @Operation(summary = "이메일 중복체크 API", description = "사용자는 회원 가입을 할 때 이메일 중복 체크를 합니다.")
    public ResponseEntity<ApiResponse<MemberRegisterEmailCheckResponse>> memberRegisterEmailCheck(
            @Valid MemberControllerRegisterEmailCheckRequest emailCheckRequest
    ) {
        MemberRegisterEmailCheckResponse response = memberService.emailCheck(emailCheckRequest.email());
        return ResponseEntity.ok(ApiResponse.create(1006, response));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 API", description = "로그인된 사용자만이 로그아웃을 할 수 있습니다. 로그인을 하지 않으면 에러가 발생합니다."
    ,security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<ApiResponse<MemberLogoutResponse>> memberLogout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @Parameter(hidden = true) @MemberAuth AuthContext authContext
    ) {
        MemberLogoutResponse response = memberService.logout(authContext.email(),
                JwtUtil.extractAccessToken(authorization)
        );
        return ResponseEntity.ok(ApiResponse.create(1011, response));
    }
}
