package com.gamsung.backend.domain.member.repository;

import com.gamsung.backend.domain.member.entity.Member;
import com.gamsung.backend.global.factory.MemberTestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberRepositoryTest {
    @Mock
    private MemberRepository memberRepository;

    @DisplayName("멤버 레포지토리 유닛 테스트 - 멤버를 이메일로 조회할 수 있다.")
    @Test
    void successToFindByMemberEmail() {
        // given
        Member member = MemberTestFactory.createMemberWithRandomValues(false);
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));

        // when
        Member storedMember = memberRepository.findByEmail(member.getEmail()).orElse(null);

        Objects.requireNonNull(storedMember);

        // then
        Assertions.assertEquals(member.getEmail(), storedMember.getEmail());
    }
}