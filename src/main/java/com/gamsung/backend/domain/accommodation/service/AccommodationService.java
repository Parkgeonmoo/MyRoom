package com.gamsung.backend.domain.accommodation.service;

import com.gamsung.backend.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.gamsung.backend.domain.accommodation.dto.response.AccommodationSummaryListResponse;
import com.gamsung.backend.domain.accommodation.dto.response.AccommodationSummaryResponse;
import com.gamsung.backend.domain.accommodation.entity.Accommodation;
import com.gamsung.backend.domain.accommodation.exception.AccommodationNotFoundException;
import com.gamsung.backend.domain.accommodation.repository.AccommodationRepository;
import com.gamsung.backend.domain.image.entity.Image;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Transactional(readOnly = true)
    public AccommodationDetailResponse findAccommodationDetailById(Long id) {
        Accommodation accommodation = findById(id);

        String accommodationImage = accommodation.getImages().get(0).getUrl();

        List<String> roomImages = accommodation.getImages().stream()
            .skip(1) // 첫 번째 이미지를 제외하고 나머지 이미지들을 가져옴
            .map(Image::getUrl)
            .collect(Collectors.toList());

        return AccommodationDetailResponse.from(accommodation, accommodationImage, roomImages);
    }

    @Transactional(readOnly = true)
    public AccommodationSummaryListResponse getAccommodationInfoByLocation(Long location,
        Pageable pageable) {
        Page<Accommodation> accommodationPage =
            accommodationRepository.findAccommodationByLocation(location, pageable);

        List<AccommodationSummaryResponse> summaryResponses =
            accommodationPage.getContent().stream()
                .map(accommodation -> AccommodationSummaryResponse.from(
                        accommodation,
                        accommodation.getImages().get(0).getUrl()
                    )
                )
                .collect(Collectors.toList());

        return AccommodationSummaryListResponse.from(
            summaryResponses,
            accommodationPage.getTotalPages(),
            accommodationPage.getNumber()
        );
    }

    @Transactional(readOnly = true)
    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id)
            .orElseThrow(AccommodationNotFoundException::new);
    }
}
