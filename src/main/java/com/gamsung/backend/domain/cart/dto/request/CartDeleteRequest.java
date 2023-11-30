package com.gamsung.backend.domain.cart.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CartDeleteRequest {

    @NotEmpty(message = "데이터 형식이 올바르지 않습니다.")
    @NotNull(message = "데이터 형식이 올바르지 않습니다.")
    private List<Long> deleteId;
}
