package com.gamsung.backend.domain.order.dto.response;

public record OrderAccommodationResponse(
        String message
) {

    public static OrderAccommodationResponse create() {
        return new OrderAccommodationResponse("결제가 성공하였습니다.");
    }
}