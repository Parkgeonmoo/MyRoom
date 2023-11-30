package com.gamsung.backend.domain.member.exception;

import com.gamsung.backend.global.exception.ErrorCode;
import com.gamsung.backend.global.exception.UnAuthException;

public class MemberNotFoundException extends UnAuthException {
    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
