package com.gamsung.backend.domain.cart.service;

import static com.gamsung.backend.global.exception.ErrorCode.ACCOMMODATION_NO_EXIST;
import static com.gamsung.backend.global.exception.ErrorCode.CART_ID_NO_EXIST;
import static com.gamsung.backend.global.exception.ErrorCode.CART_LIMIT_OVER;

import com.gamsung.backend.domain.accommodation.entity.Accommodation;
import com.gamsung.backend.domain.accommodation.repository.AccommodationRepository;
import com.gamsung.backend.domain.cart.dto.request.CartDeleteRequest;
import com.gamsung.backend.domain.cart.dto.request.CartEntryRequest;
import com.gamsung.backend.domain.cart.dto.response.CartFindResponse;
import com.gamsung.backend.domain.cart.entity.Cart;
import com.gamsung.backend.domain.cart.exception.CartException;
import com.gamsung.backend.domain.cart.repository.CartRepository;
import com.gamsung.backend.domain.member.entity.Member;
import com.gamsung.backend.domain.member.repository.MemberRepository;
import com.gamsung.backend.domain.order.entity.Order;
import com.gamsung.backend.domain.order.repository.OrderRepositorySupport;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final AccommodationRepository accommodationRepository;
    private final MemberRepository memberRepository;
    private final OrderRepositorySupport orderRepositorySupport;

    @Transactional
    public void entryMyCart(CartEntryRequest cartEntryRequest,Long memberId){

        Optional<Member> findMember = memberRepository.findById(memberId);
        Member member = findMember.get();

        Accommodation accommodation = accommodationRepository.findById(cartEntryRequest.getAccommodationId())
                .orElseThrow(() -> new CartException(ACCOMMODATION_NO_EXIST));

        int currentCartCount = cartRepository.countByMember(member);
        int newCartCount = currentCartCount + 1;

        if (newCartCount > 10) {
            throw new CartException(CART_LIMIT_OVER);
        }

        Cart cart = Cart.builder()
                .accommodation(accommodation)
                .member(member)
                .startDate(cartEntryRequest.getStartDate())
                .endDate(cartEntryRequest.getEndDate())
                .reservationPeople(cartEntryRequest.getPeople())
                .price(cartEntryRequest.getCartPrice())
                .isDeleted(false)
                .build();


        cartRepository.save(cart);



    }

    @Transactional(readOnly = true)
    public List<CartFindResponse> findMyCart(Long memberId) {

        List<Cart> myCartList = cartRepository.findAllByMemberId(memberId);

        for (Cart cart : myCartList) {
            boolean isSoldOut = isItemSoldOut(cart);
            cart.setIsDeleted(isSoldOut);
            // 만약 해당 상품이 품절이면 isDeleted를 true로 설정하고, 그렇지 않으면 false로 설정
        }


        return myCartList.stream()
                .map(CartFindResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMyCart(CartDeleteRequest cartDeleteRequest, Long memberId) {
        List<Long> deleteId = cartDeleteRequest.getDeleteId();


        for (long id : deleteId) {
            Optional<Cart> cartOptional = cartRepository.findById(id);
            if (cartOptional.isPresent()) {
                cartRepository.delete(cartOptional.get());
            } else {
                // 해당 id에 대한 Cart 아이템이 존재하지 않는 경우 예외 던지기
                throw new CartException(CART_ID_NO_EXIST);
            }

        }

    }
    private boolean isItemSoldOut(Cart cart) {
        LocalDate startDate = cart.getStartDate();
        LocalDate endDate = cart.getEndDate();

        Long accommodationId = cart.getAccommodation().getId();

        // 주문 테이블에서 해당 숙소에 대한 예약이 있는지 확인
        Optional<Order> order = orderRepositorySupport.findFirstByAccommodationIdAndEndDateGreaterThanAndStartDateLessThanOrderByStartDateAsc(
                accommodationId, startDate, endDate);



        return order.isPresent();
    }

}

