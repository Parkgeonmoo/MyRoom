package com.gamsung.backend.domain.cart.repository;

import com.gamsung.backend.domain.cart.entity.Cart;
import com.gamsung.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {

    public int countByMember(Member member);
    List<Cart> findAllByMemberId(Long memberId);





}
