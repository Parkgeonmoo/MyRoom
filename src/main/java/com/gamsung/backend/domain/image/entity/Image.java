package com.gamsung.backend.domain.image.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gamsung.backend.domain.accommodation.entity.Accommodation;
import com.gamsung.backend.global.common.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accommodation_image")
@Getter
public class Image extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Integer imgType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    @JsonIgnore
    Accommodation accommodation;

    @Column(nullable = false)
    String url;

    @Builder
    private Image(
        Accommodation accommodation,
        Integer imgType,
        String url
    ) {
        this.accommodation = accommodation;
        this.imgType = imgType;
        this.url = url;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
