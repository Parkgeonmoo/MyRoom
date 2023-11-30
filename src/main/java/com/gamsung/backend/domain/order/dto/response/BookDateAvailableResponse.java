package com.gamsung.backend.domain.order.dto.response;

public record BookDateAvailableResponse(
        String message
) {

    public static BookDateAvailableResponse create() {
        return new BookDateAvailableResponse("해당 날짜는 예약 가능합니다.");
    }
}
