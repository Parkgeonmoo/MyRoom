package com.gamsung.backend.domain.cart.dto.response;

public record CartDeleteResponse(
        String message
) {

    public static CartDeleteResponse create() { return new CartDeleteResponse("선택한 항목들이 삭제되었습니다.");}
}
