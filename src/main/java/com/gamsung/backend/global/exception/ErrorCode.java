package com.gamsung.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //상태 코드,메시지를 담아서 ENUM 타입으로 쓰면 됩니다.

    // 멤버
    MEMBER_NOT_FOUND("1001", "회원 아이디가 존재하지 않습니다."),
    MEMBER_LOGIN_WRONG_PASSWORD("1002", "비밀번호가 올바르지 않습니다."),
    MEMBER_ALREADY_EXISTED("1007", "회원가입이 되어있는 이메일입니다."),

    // JWT
    JWT_EXPIRED_REFRESH_TOKEN("1009", "리프레시 토큰이 만료되었습니다."),
    JWT_INVALID_REFRESH_TOKEN("1010", "리프레시 토큰이 유효하지 않습니다."),

    // 주문
    BOOK_DATE_UNAVAILABLE("2002", "예약이 불가능한 날짜입니다."),
    ORDER_SOLD_OUT("2004", "결제가 실패했습니다. 예약이 불가능한 날짜가 있습니다."),

    // 숙박
    ACCOMMODATION_NO_EXIST("3002","해당 상품의 정보가 없습니다."),

    //장바구니
    CART_LIMIT_OVER("4002","장바구니가 최대 개수인 10개를 초과했습니다."),
    CART_ID_NO_EXIST("4004","삭제하고자 하는 제품이 존재하지 않습니다."),
    // 공통
    JWT_INVALID_ACCESS_TOKEN("5000", "유효하지 않은 엑세스 토큰입니다."),
    JWT_EXPIRED_ACCESS_TOKEN("5001", "액세스 토큰이 만료되었습니다."),
    SERVICE_ERREOR("5002", "서비스 오류입니다."),
    DATA_NOT_ALLOW_TYPE("5003", "데이터 형식이 올바르지 않습니다."),

    ;


    private final String code;
    private final String message;
}
