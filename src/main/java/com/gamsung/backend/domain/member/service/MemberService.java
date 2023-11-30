package com.gamsung.backend.domain.member.service;

import com.gamsung.backend.domain.member.dto.request.MemberLoginRequest;
import com.gamsung.backend.domain.member.dto.request.MemberRegisterRequest;
import com.gamsung.backend.domain.member.dto.response.MemberLoginResponse;
import com.gamsung.backend.domain.member.dto.response.MemberLogoutResponse;
import com.gamsung.backend.domain.member.dto.response.MemberRegisterEmailCheckResponse;
import com.gamsung.backend.domain.member.dto.response.MemberRegisterResponse;
import com.gamsung.backend.domain.member.entity.Member;
import com.gamsung.backend.domain.member.exception.MemberAlreadyExistedException;
import com.gamsung.backend.domain.member.exception.MemberLoginWrongPasswordException;
import com.gamsung.backend.domain.member.exception.MemberNotFoundException;
import com.gamsung.backend.domain.member.repository.MemberRepository;
import com.gamsung.backend.global.jwt.dto.JwtPayload;
import com.gamsung.backend.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) {
        Member storedMember = memberRepository.findByEmail(memberLoginRequest.email())
                .orElseThrow(MemberNotFoundException::new);

        if (!passwordEncoder.matches(memberLoginRequest.password(), storedMember.getPassword())) {
            throw new MemberLoginWrongPasswordException();
        }

        return MemberLoginResponse.from(
                jwtService.createTokenPair(JwtPayload.from(storedMember.getId(), storedMember.getEmail())),
                storedMember.getName(),
                storedMember.getEmail());
    }

    @Transactional
    public MemberRegisterResponse register(MemberRegisterRequest memberRegisterRequest) {
        validateMemberEmail(memberRegisterRequest.email());

        memberRepository.save(
                Member.builder()
                        .email(memberRegisterRequest.email())
                        .password(passwordEncoder.encode(memberRegisterRequest.password()))
                        .name(memberRegisterRequest.name())
                        .build()
        );

        return MemberRegisterResponse.create();
    }

    @Transactional(readOnly = true)
    public MemberRegisterEmailCheckResponse emailCheck(String email) {
        validateMemberEmail(email);

        return MemberRegisterEmailCheckResponse.create();
    }

    @Transactional
    public MemberLogoutResponse logout(String email, String accessToken) {
        jwtService.deleteRefreshTokenAndAddAccessTokenToBlackList(email, accessToken);
        return MemberLogoutResponse.create();
    }

    private void validateMemberEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberAlreadyExistedException();
        }
    }
}
