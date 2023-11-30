package com.gamsung.backend.domain.order.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SoldOutErrorResponse (
    String message,
    List<SoldOutOrder> soldOutOrders
) {
    public static SoldOutErrorResponse create(String message, List<SoldOutOrder> soldOutOrders) {
        return new SoldOutErrorResponse(message, soldOutOrders);
    }
}
