package com.gamsung.backend.domain.accommodation.dto.response;

import com.gamsung.backend.domain.accommodation.entity.Accommodation;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Builder
public record AccommodationSummaryResponse(
        Long accommodationId,
        String accommodationName,
        String shortAddress,
        Long accommodationPrice,
        String accommodationImg
) {
    public static AccommodationSummaryResponse from(Accommodation accommodation , String accommodationImg){
        return AccommodationSummaryResponse.builder()
            .accommodationId(accommodation.getId())
            .accommodationName(accommodation.getName())
            .shortAddress(makeShortAddress(accommodation.getAddress()))
            .accommodationPrice(accommodation.getPrice())
            .accommodationImg(accommodationImg)
            .build();
    }

    private static String makeShortAddress(String address) {
        String[] parts = address.split(" ");
        return parts.length > 1 ? parts[0] + " " + parts[1] : address;
    }
}
