package com.hao.base.common.exception;

public class ParamErrorException extends BaseException {

    public ParamErrorException(String message) {
        super(400, message);
    }
}
