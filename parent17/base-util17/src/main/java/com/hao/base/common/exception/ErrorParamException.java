package com.hao.base.common.exception;

import com.hao.base.common.pojo.constant.Code;

public class ErrorParamException extends BaseException {

    public ErrorParamException(String message) {
        super(Code.ERROR_PARAM, message);
    }
}
