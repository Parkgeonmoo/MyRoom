package com.gamsung.backend.domain.member.exception;

import com.gamsung.backend.global.exception.BaseException;
import com.gamsung.backend.global.exception.ErrorCode;

public class MemberLoginWrongPasswordException extends BaseException {
    public MemberLoginWrongPasswordException() {
        super(ErrorCode.MEMBER_LOGIN_WRONG_PASSWORD);
    }
}
