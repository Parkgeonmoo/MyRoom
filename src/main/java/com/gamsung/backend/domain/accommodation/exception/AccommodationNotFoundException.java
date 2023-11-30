package com.gamsung.backend.domain.accommodation.exception;


import com.gamsung.backend.global.exception.BaseException;
import com.gamsung.backend.global.exception.ErrorCode;

public class AccommodationNotFoundException extends BaseException {

    public AccommodationNotFoundException(){
        super(ErrorCode.ACCOMMODATION_NO_EXIST);
    }
}
