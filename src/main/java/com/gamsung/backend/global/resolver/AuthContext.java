package com.gamsung.backend.global.resolver;

public record AuthContext(
        Long id,
        String email
) {
    public static AuthContext from(String id, String email) {
        return new AuthContext(Long.parseLong(id), email);
    }
}
