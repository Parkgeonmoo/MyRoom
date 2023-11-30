package com.gamsung.backend.domain.order.repository;

import com.gamsung.backend.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByMemberIdOrderByCreatedAtDesc(long memberId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.accommodationId = ?1 AND o.startDate < ?2 AND o.endDate > ?3  ")
    Optional<Order> existsByAccommodationIdAndStartDateBeforeAndEndDateAfter(
            @Param("accommodationId") long accommodationId,
            @Param("endDate") LocalDate endDate,
            @Param("startDate") LocalDate startDate);

}
