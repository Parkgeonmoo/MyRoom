package com.gamsung.backend.domain.order.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderAccommodationRequest {

    @NotNull(message = "데이터 형식이 올바르지 않습니다.")
    long accommodationId;

    @NotNull(message = "데이터 형식이 올바르지 않습니다.")
    int peopleNumber;

    @NotNull(message = "데이터 형식이 올바르지 않습니다.")
    LocalDate startDate;

    @NotNull(message = "데이터 형식이 올바르지 않습니다.")
    LocalDate endDate;

    @NotEmpty(message = "데이터 형식이 올바르지 않습니다.")
    String representativeName;

    @NotEmpty(message = "데이터 형식이 올바르지 않습니다.")
    String representativeEmail;

    @NotNull(message = "데이터 형식이 올바르지 않습니다.")
    int orderPrice;

    @NotNull(message = "데이터 형식이 올바르지 않습니다.")
    long cartId;
}
