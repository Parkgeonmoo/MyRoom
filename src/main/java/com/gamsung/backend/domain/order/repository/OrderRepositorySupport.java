package com.gamsung.backend.domain.order.repository;

import com.gamsung.backend.domain.order.entity.Order;
import com.gamsung.backend.domain.order.entity.QOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class OrderRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private final QOrder order = QOrder.order;

    public OrderRepositorySupport(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public Optional<Order> findFirstByAccommodationIdAndEndDateGreaterThanAndStartDateLessThanOrderByStartDateAsc(
            Long accommodationId, LocalDate startDate, LocalDate endDate) {

        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(order)
                        .where(
                                order.accommodationId.eq(accommodationId),
                                order.endDate.gt(startDate),
                                order.startDate.lt(endDate)
                        )
                        .orderBy(order.startDate.asc())
                        .fetchFirst()
        );
    }

}

