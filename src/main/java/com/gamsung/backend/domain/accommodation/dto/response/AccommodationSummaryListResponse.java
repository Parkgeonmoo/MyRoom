package com.gamsung.backend.domain.accommodation.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record AccommodationSummaryListResponse(
    List<AccommodationSummaryResponse> accommodations,
    Integer totalPage,
    Integer pageNumber

) {

    public static AccommodationSummaryListResponse from(List<AccommodationSummaryResponse> accommodations,int totalPage,int pageNumber){
        return AccommodationSummaryListResponse.builder()
            .accommodations(accommodations)
            .totalPage(totalPage)
            .pageNumber(pageNumber+1)
            .build();
    }
}
