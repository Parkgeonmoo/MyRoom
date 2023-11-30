package com.gamsung.backend.domain.order.entity;

import com.gamsung.backend.domain.accommodation.entity.Accommodation;
import com.gamsung.backend.domain.member.entity.Member;
import com.gamsung.backend.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(targetEntity = Accommodation.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", insertable = false, updatable = false)
    private Accommodation accommodation;

    @Column(name = "accommodation_id")
    private Long accommodationId;

    @Column(name = "people_number",nullable = false)
    private int peopleNumber;

    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @Column(name = "representative_name",nullable = false)
    private String representativeName;

    @Column(name = "representative_email",nullable = false)
    private String representativeEmail;

    @Column(name = "order_price",nullable = false)
    private long orderPrice;

    private Order(Long memberId, Long accommodationId, int peopleNumber,
                  LocalDate startDate, LocalDate endDate, String representativeName,
                  String representativeEmail, long orderPrice) {
        this.memberId = memberId;
        this.accommodationId = accommodationId;
        this.peopleNumber = peopleNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.representativeName = representativeName;
        this.representativeEmail = representativeEmail;
        this.orderPrice = orderPrice;
    }

    public static Order of(Long memberId, Long accommodationId, int peopleNumber,
                           LocalDate startDate, LocalDate endDate, String representativeName,
                           String representativeEmail, long orderPrice) {
        return new Order(memberId, accommodationId, peopleNumber,
                startDate, endDate, representativeName,
                representativeEmail, orderPrice);
    }
}
