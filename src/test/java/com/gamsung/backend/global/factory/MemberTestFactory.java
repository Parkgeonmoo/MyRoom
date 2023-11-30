package com.gamsung.backend.global.factory;

import com.gamsung.backend.domain.member.entity.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.ThreadLocalRandom;

public class MemberTestFactory {

    private final static int RANDOM_BOUND = 100000000;
    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static Member createMemberWithRandomValues(boolean encoded) {
        String name = String.format("test%d", ThreadLocalRandom.current().nextInt(RANDOM_BOUND));
        String email = String.format("%s@test.com", name);
        String pass = String.valueOf(ThreadLocalRandom.current().nextInt(RANDOM_BOUND));
        if (encoded) pass = passwordEncoder.encode(pass);
        return Member.builder()
                .email(email)
                .name(name)
                .password(pass)
                .build();
    }
}
