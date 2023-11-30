package com.gamsung.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationCode {

    DATA_NOT_ALLOW_TYPE("데이터 형식이 올바르지 않습니다."),
    SERVICE_ERREOR("서비스 오류입니다."),

    ;

    private String message;
}
