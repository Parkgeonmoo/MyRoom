package com.gamsung.backend.global.security;

import com.gamsung.backend.global.jwt.security.JwtAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomJWTSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomMember> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomMember annotation) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        final JwtAuthenticationToken jwtAuthenticationToken
                = JwtAuthenticationToken.authenticated(annotation.id(), annotation.email());

        securityContext.setAuthentication(jwtAuthenticationToken);

        return securityContext;
    }
}
