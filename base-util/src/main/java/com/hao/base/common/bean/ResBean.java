package com.hao.base.common.bean;

import com.hao.base.common.exception.BaseException;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 自定义响应包括类
 * @param <T> 数据类型
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class ResBean<T> {

    private int status; // 状态码
    private String message; // 提示信息
    private T data; // 数据

    public T checkAndGetData() {
        if (this.data == null) {
            throw new BaseException(this.status, this.message);
        }
        return this.data;
    }

    // 给定状态码和提示消息构建响应体
    public static <T> ResBean<T> build(int status, String message, T data) {
        return ResBean.<T>builder().status(status).message(message).data(data).build();
    }

    // 常用通用状态码
    private static final int OK = 200;
    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int CONFLICT = 409;
    private static final int INTERNAL_SERVER_ERROR = 500;
}
