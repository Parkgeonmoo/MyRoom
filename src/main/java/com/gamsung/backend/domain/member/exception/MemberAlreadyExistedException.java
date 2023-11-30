package com.gamsung.backend.domain.member.exception;

import com.gamsung.backend.global.exception.BaseException;
import com.gamsung.backend.global.exception.ErrorCode;

public class MemberAlreadyExistedException extends BaseException {
    public MemberAlreadyExistedException() {
        super(ErrorCode.MEMBER_ALREADY_EXISTED);
    }
}
