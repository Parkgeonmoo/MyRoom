package com.gamsung.backend.domain.order.exception;

import com.gamsung.backend.domain.order.dto.response.SoldOutOrder;
import com.gamsung.backend.global.exception.BaseException;
import com.gamsung.backend.global.exception.ErrorCode;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderSoldOutException extends BaseException {

    private final List<SoldOutOrder> soldOutOrders;

    public OrderSoldOutException(List<SoldOutOrder> soldOutOrders) {
        super(ErrorCode.ORDER_SOLD_OUT);
        this.soldOutOrders = soldOutOrders;
    }
}