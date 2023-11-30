package com.gamsung.backend.domain.order.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SoldOutOrder(
    long accommodationId,
    LocalDate startDate,
    LocalDate endDate
){
    public static SoldOutOrder from(long accommodationId,
                                    LocalDate startDate, LocalDate endDate){
        return new SoldOutOrder(accommodationId,startDate,endDate);
    }
}
