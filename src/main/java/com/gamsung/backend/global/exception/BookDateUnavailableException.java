package com.gamsung.backend.global.exception;

import lombok.Getter;


@Getter
public class BookDateUnavailableException extends BaseException {
    public BookDateUnavailableException() {
        super(ErrorCode.BOOK_DATE_UNAVAILABLE);
    }
}
