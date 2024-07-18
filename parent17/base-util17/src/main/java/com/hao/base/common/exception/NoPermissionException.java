package com.hao.base.common.exception;

import com.hao.base.common.pojo.constant.Code;

public class NoPermissionException extends BaseException {

    public NoPermissionException() {
        this("权限不足");
    }

    public NoPermissionException(String message) {
        super(Code.ERROR_PARAM, message);
    }
}
