package com.gamsung.backend.domain.order.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrderResponse(
        LocalDate orderDate,
        long accommodationId,
        String accommodationName,
        String accommodationImg,
        int peopleNumber,
        LocalDate startDate,
        LocalDate endDate,
        String representativeName,
        long orderPrice
) {
   public static OrderResponse from(LocalDate orderDate, long accommodationId, String accommodationName,
                                    String accommodationImg, int peopleNumber, LocalDate startDate,
                                    LocalDate endDate, String representativeName, long orderPrice) {
      return new OrderResponse(orderDate, accommodationId, accommodationName,
              accommodationImg, peopleNumber, startDate,
              endDate, representativeName, orderPrice);
   }
}