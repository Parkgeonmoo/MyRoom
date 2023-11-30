package com.gamsung.backend.global.jwt.security;

import com.gamsung.backend.global.resolver.AuthContext;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final AuthContext principal;
    private String credentials;

    private JwtAuthenticationToken(AuthContext principal, String credentials, boolean isAuthenticated) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(isAuthenticated);
    }

    private JwtAuthenticationToken(AuthContext principal, boolean isAuthenticated) {
        super(AuthorityUtils.createAuthorityList("ROLE_USER"));
        this.principal = principal;
        setAuthenticated(isAuthenticated);
    }

    public static JwtAuthenticationToken unauthenticated(String accessToken) {
        return new JwtAuthenticationToken(null, accessToken, false);
    }

    public static JwtAuthenticationToken authenticated(String id, String email) {
        return new JwtAuthenticationToken(AuthContext.from(id, email), true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
