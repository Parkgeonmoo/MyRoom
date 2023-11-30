package com.gamsung.backend.domain.accommodation.dto.response;

import com.gamsung.backend.domain.accommodation.entity.Accommodation;
import java.util.List;
import lombok.Builder;

@Builder
public record AccommodationDetailResponse(
    String accommodationName,
    String description,
    String address,
    Long limitPeople,
    Long accommodationPrice,
    String accommodationImg,
    List<String> roomImg
) {

    public static AccommodationDetailResponse from(
        Accommodation accommodation,
        String accommodationImg,
        List<String> roomImages
    ) {
        return AccommodationDetailResponse.builder()
            .accommodationName(accommodation.getName())
            .description(accommodation.getDescription())
            .address(accommodation.getAddress())
            .limitPeople(accommodation.getLimitPeople())
            .accommodationPrice(accommodation.getPrice())
            .accommodationImg(accommodationImg)
            .roomImg(roomImages)
            .build();
    }
}
