package com.gamsung.backend.domain.cart.dto.response;

public record CartEntryResponse(
        String message
) {

    public static CartEntryResponse create() { return new CartEntryResponse("장바구니에 추가되었습니다.");}
}
