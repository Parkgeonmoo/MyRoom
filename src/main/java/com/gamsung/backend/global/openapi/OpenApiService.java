package com.gamsung.backend.global.openapi;

import static com.gamsung.backend.global.openapi.UrlConstants.ACCOMMODATION_PATH;
import static com.gamsung.backend.global.openapi.UrlConstants.ARRANGE;
import static com.gamsung.backend.global.openapi.UrlConstants.CONTENT_TYPE_ID;
import static com.gamsung.backend.global.openapi.UrlConstants.DEFAULT_NUM_OF_ROWS;
import static com.gamsung.backend.global.openapi.UrlConstants.DESCRIPTION_PATH;
import static com.gamsung.backend.global.openapi.UrlConstants.END_POINT;
import static com.gamsung.backend.global.openapi.UrlConstants.MOBILE_APP;
import static com.gamsung.backend.global.openapi.UrlConstants.MOBILE_OS;
import static com.gamsung.backend.global.openapi.UrlConstants.PAGE_NO;
import static com.gamsung.backend.global.openapi.UrlConstants.ROOM_PATH;
import static com.gamsung.backend.global.openapi.UrlConstants.TYPE;
import static com.gamsung.backend.global.openapi.UrlConstants.YES;

import com.fasterxml.jackson.databind.JsonNode;
import com.gamsung.backend.domain.accommodation.entity.Accommodation;
import com.gamsung.backend.domain.accommodation.repository.AccommodationRepository;
import com.gamsung.backend.domain.image.entity.Image;
import com.gamsung.backend.domain.image.repository.ImageRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final AccommodationRepository accomodationRepository;
    private final ImageRepository imageRepository;

    @Value("${open-api.key}")
    private String decodeApiKey;

    public void getAccommodationInfo() {
        processAccommodationInfo();
    }

    private Optional<JsonNode> makeApiCall(String url) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<JsonNode> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
            return Optional.ofNullable(responseEntity.getBody());
        } catch (UnknownContentTypeException e) {
            System.out.println(url);
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    private String buildApiUrl(String baseUrl, String... queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        for (int i = 0; i < queryParams.length; i += 2) {
            String key = queryParams[i];
            String value = queryParams[i + 1];
            builder.queryParam(key, value);
        }
        return builder.build().toUriString();
    }

    private void processAccommodationInfo() {
        String accommodationInfoUrl = buildApiUrl(
            END_POINT + ACCOMMODATION_PATH
            , "numOfRows", DEFAULT_NUM_OF_ROWS
            , "pageNo", PAGE_NO
            , "mobileOS", MOBILE_OS
            , "MobileApp", MOBILE_APP
            , "_type", TYPE
            , "arrange", ARRANGE
            , "serviceKey", decodeApiKey
        );
        Optional<JsonNode> responseNode = makeApiCall(accommodationInfoUrl);
        try {
            JsonNode items =
                responseNode.get().path("response").path("body").path("items").path("item");

            for (JsonNode item : items) {
                String location = item.path("areacode").asText();
                String address = item.path("addr1").asText();
                String contentId = item.path("contentid").asText();
                String firstImage = item.path("firstimage").asText();
                String name = item.path("title").asText();

                if (location.isEmpty() || address.isEmpty() || contentId.isEmpty()
                    || firstImage.isEmpty() || name.isEmpty() || !endsWithJpgOrJpeg(
                    firstImage)) {
                    continue;
                }

                Image accommodationImage = Image.builder().imgType(1).url(firstImage).build();
                processAccommodationDescription(contentId, accommodationImage, name, address,
                    location);
            }
        } catch (Exception e) {
            System.out.println("AccommodationInfo Error : " + e.getMessage());
        }
    }

    private void processAccommodationDescription(String contentId, Image accommodationImage,
        String name,
        String address, String location) {
        String accommodationDescriptionUrl = buildApiUrl(
            END_POINT + DESCRIPTION_PATH
            , "numOfRows", DEFAULT_NUM_OF_ROWS
            , "mobileOS", MOBILE_OS
            , "MobileApp", MOBILE_APP
            , "contentId", contentId
            , "contentTypeId", CONTENT_TYPE_ID
            , "overviewYN", YES
            , "_type", TYPE
            , "serviceKey", decodeApiKey
        );
        Optional<JsonNode> responseNode = makeApiCall(accommodationDescriptionUrl);
        try {
            JsonNode items =
                responseNode.get().path("response").path("body").path("items").path("item");

            // 여러 객실 중 첫 번째 객실만 사용한다.
            String description = items.get(0).path("overview").asText();
            if (description.isEmpty()) {
                return;
            }

            processRoomInfo(contentId, accommodationImage, name, address, location, description);
        } catch (Exception e) {
            System.out.println("Accommodation Description Error : " + e.getMessage());
        }
    }

    private void processRoomInfo(String contentId, Image accommodationImage, String name,
        String address, String location, String description) {
        String roomInfoUrl = buildApiUrl(
            END_POINT + ROOM_PATH
            , "MobileOS", MOBILE_OS
            , "MobileApp", MOBILE_APP
            , "contentId", contentId
            , "contentTypeId", CONTENT_TYPE_ID
            , "_type", TYPE
            , "serviceKey", decodeApiKey
        );

        Optional<JsonNode> responseNode = makeApiCall(roomInfoUrl);
        try {
            JsonNode items =
                responseNode.get().path("response").path("body").path("items").path("item");

            String accommodationPrice = items.get(0).path("roomoffseasonminfee1").asText();
            String limitPeople = items.get(0).path("roommaxcount").asText();

            if (accommodationPrice.equals("0") || limitPeople.equals("0")) {
                return;
            }

            List<Image> roomImages = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                String imageUrl = items.get(0).path("roomimg" + i).asText();
                if (!imageUrl.isEmpty() && endsWithJpgOrJpeg(imageUrl)) {
                    Image roomImage = Image.builder().imgType(2).url(imageUrl).build();
                    roomImages.add(roomImage);
                }
            }

            saveAccommodationAndImages(accommodationImage,
                roomImages, name,
                address, location, description, accommodationPrice, limitPeople);

        } catch (Exception e) {
            System.out.println("RoomInfo Error : " + e.getMessage());
        }
    }

    @Transactional
    public void saveAccommodationAndImages(Image accomodationImage,
        List<Image> roomImages, String name,
        String address, String location, String description, String accommodationPrice,
        String limitPeople
    ) {

        Accommodation accommodation = Accommodation.builder()
            .name(name)
            .description(description)
            .address(address)
            .location(Long.valueOf(location))
            .limitPeople(Long.valueOf(limitPeople))
            .price(Long.valueOf(accommodationPrice))
            .build();

        // Accomodation Info 저장
        accomodationRepository.save(accommodation);

        // Accomodation Image 저장
        accomodationImage.setAccommodation(accommodation);
        imageRepository.save(accomodationImage);
        accommodation.addImage(accomodationImage);

        // Room Images 저장
        for (Image roomImage : roomImages) {
            roomImage.setAccommodation(accommodation);
            imageRepository.save(roomImage);
            accommodation.addImage(roomImage);
        }

//        printSaveAccommodationInfo(accommodation);
    }

    private boolean endsWithJpgOrJpeg(String url) {
        int lastDotIndex = url.lastIndexOf(".");
        if (lastDotIndex == -1 || lastDotIndex == url.length() - 1) {
            return false;
        }
        String extension = url.substring(lastDotIndex + 1).toLowerCase();
        return extension.equals("jpg") || extension.equals("jpeg");
    }

    private void printSaveAccommodationInfo(Accommodation accommodation) {
        System.out.println("Address: " + accommodation.getAddress());
        System.out.println("AreaCode: " + accommodation.getLocation());
        System.out.println("Title: " + accommodation.getName());
        System.out.println("RoomFee: " + accommodation.getPrice());
        System.out.println("MaxCount: " + accommodation.getLimitPeople());
        System.out.println("description: " + accommodation.getDescription());
        System.out.println("Image: ");
        accommodation.getImages().stream()
            .forEach(image1 -> System.out.println(
                "Type: " + image1.getImgType() + ", URL: "
                    + image1.getUrl()));
        System.out.println("---------------------------------");
    }
}
