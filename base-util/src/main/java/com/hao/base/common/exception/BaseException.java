package com.hao.base.common.exception;

import lombok.*;
import lombok.experimental.Accessors;

// 基础全局异常类，继承该类的异常都会被 Spring 的全局异常处理器捕捉，并转化为 ResBean 对应的 json 形式
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class BaseException extends RuntimeException {

    private int code;
    private String message;

}
