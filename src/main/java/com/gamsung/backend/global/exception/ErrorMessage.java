package com.gamsung.backend.global.exception;

public record ErrorMessage(
        String message
) {
    public static ErrorMessage create(String message) {
        return new ErrorMessage(message);
    }
}
