package com.gamsung.backend.domain.order.service;

import com.gamsung.backend.domain.accommodation.service.AccommodationService;
import com.gamsung.backend.domain.cart.repository.CartRepository;
import com.gamsung.backend.domain.order.dto.request.OrderAccommodationRequest;
import com.gamsung.backend.domain.order.dto.response.*;
import com.gamsung.backend.domain.order.entity.Order;
import com.gamsung.backend.domain.order.exception.OrderSoldOutException;
import com.gamsung.backend.domain.order.repository.OrderRepository;
import com.gamsung.backend.global.exception.BookDateUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final int ID_ZERO = 0;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AccommodationService accommodationService;

    @Transactional
    public OrderAccommodationResponse orderAccommodation(List<OrderAccommodationRequest> orderAccommodationRequestList,
                                                         long userId) {
        List<Order> orderList = new ArrayList<>();
        List<Long> deleteCartIdList = new ArrayList<>();
        List<SoldOutOrder> soldOutOrders = new ArrayList<>();

        for (OrderAccommodationRequest orderRequest : orderAccommodationRequestList) {
            accommodationService.findById(orderRequest.getAccommodationId());
            Order order = Order.of(
                    userId,
                    orderRequest.getAccommodationId(), orderRequest.getPeopleNumber(),
                    orderRequest.getStartDate(), orderRequest.getEndDate(),
                    orderRequest.getRepresentativeName(), orderRequest.getRepresentativeEmail(),
                    orderRequest.getOrderPrice()
            );

            if (orderRepository.existsByAccommodationIdAndStartDateBeforeAndEndDateAfter(
                    order.getAccommodationId(), order.getEndDate(), order.getStartDate()).isPresent()
            ) {
                soldOutOrders.add(SoldOutOrder.from(order.getAccommodationId(),
                        order.getStartDate(), order.getEndDate()));
                continue;
            }

            orderList.add(order);

            if(isFromCart(orderRequest.getCartId())) continue;
            deleteCartIdList.add(orderRequest.getCartId());
        }

        if(!soldOutOrders.isEmpty()){
            throw new OrderSoldOutException(soldOutOrders);
        }

        orderRepository.saveAll(orderList);
        cartRepository.deleteAllByIdInBatch(deleteCartIdList);

        return OrderAccommodationResponse.create();
    }

    @Transactional(readOnly = true)
    public BookDateAvailableResponse checkBookDate(long id, LocalDate startDate, LocalDate endDate) {
        accommodationService.findById(id);
        Optional<Order> soldOrder = orderRepository.existsByAccommodationIdAndStartDateBeforeAndEndDateAfter(id, endDate, startDate);
        if (soldOrder.isPresent()) {
            throw new BookDateUnavailableException();
        }
        return BookDateAvailableResponse.create();
    }

    @Transactional(readOnly = true)
    public FindOrderListResponse getMemberOrdersList(Pageable pageable, long id) {
        Page<Order> orderPages = orderRepository.findByMemberIdOrderByCreatedAtDesc(id, pageable);
        List<Order> orderList = orderPages.getContent();

        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orderList) {
            OrderResponse orderResponse = OrderResponse.from(
                    order.getCreatedAt().toLocalDate(),
                    order.getAccommodationId(),
                    order.getAccommodation().getName(),
                    order.getAccommodation().getImages().get(0).getUrl(),
                    order.getPeopleNumber(),
                    order.getStartDate(),
                    order.getEndDate(),
                    order.getRepresentativeName(),
                    order.getOrderPrice());
            orderResponses.add(orderResponse);
        }

        return new FindOrderListResponse(orderResponses, orderPages.getTotalPages(), orderPages.getNumber() + 1);
    }

    private boolean isFromCart(long cartId) {
        return cartId == ID_ZERO;
    }

}
