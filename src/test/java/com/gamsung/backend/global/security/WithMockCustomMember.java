package com.gamsung.backend.global.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomJWTSecurityContextFactory.class)
public @interface WithMockCustomMember {
    String id() default "1234";
    String email() default "test@test.com";
}
