package com.gamsung.backend.domain.cart.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CartEntryRequest {

    @NotNull(message = "데이터 형식이 올바르지 않습니다.")
    private Long accommodationId;
    @NotEmpty(message = "데이터 형식이 올바르지 않습니다.")
    private String name;
    @NotEmpty(message = "데이터 형식이 올바르지 않습니다.")
    private String address;
    @NotNull(message = "데이터 형식이 올바르지 않습니다.")
    private LocalDate startDate;
    @NotNull(message = "데이터 형식이 올바르지 않습니다.")
    private LocalDate endDate;
    @NotNull(message = "데이터 형식이 올바르지 않습니다.")
    private Long people;
    @NotNull(message = "데이터 형식이 올바르지 않습니다.")
    private Long cartPrice;
    @NotEmpty(message = "데이터 형식이 올바르지 않습니다.")
    private String accommodationImg;


}
