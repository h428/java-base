package com.hao.base.common.pojo.wrapper;

import com.hao.base.common.pojo.constant.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 自定义响应包括类
 *
 * @param <T> 数据类型
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class Res<T> {

    private int code; // 请求代码，可能是通用代码或业务代码

    private String message; // 提示信息

    private T data; // 响应数据

    // 给定状态码和提示消息构建响应体
    public static <T> Res<T> build(int code, String message, T data) {
        return Res.<T>builder().code(code).message(message).data(data).build();
    }

    public static <T> Res<T> success(String message, T data) {
        return Res.build(Code.SUCCESS, message, data);
    }

    public static <T> Res<T> success(String message) {
        return Res.build(Code.SUCCESS, message, null);
    }

    public static <T> Res<T> success(T data) {
        return Res.build(Code.SUCCESS, "请求成功", data);
    }


    public static <T> Res<T> success() {
        return Res.build(Code.SUCCESS, "请求成功", null);
    }

    public static <T> Res<T> unauthorized(String message) {
        return Res.build(Code.UNAUTHORIZED, message, null);
    }

    public static <T> Res<T> unauthorized() {
        return Res.build(Code.UNAUTHORIZED, "未认证用户", null);
    }

    public static <T> Res<T> forbidden(String message) {
        return Res.build(Code.FORBIDDEN, message, null);
    }

    public static <T> Res<T> forbidden() {
        return Res.build(Code.FORBIDDEN, "权限不足", null);
    }

    public static <T> Res<T> errorParam(String message) {
        return Res.build(Code.ERROR_PARAM, message, null);
    }

    public static <T> Res<T> errorParam() {
        return Res.build(Code.ERROR_PARAM, "参数输入有误", null);
    }

    public static <T> Res<T> businessException(String message) {
        return Res.build(Code.BUSINESS_EXCEPTION, message, null);
    }

    public static <T> Res<T> businessException() {
        return Res.build(Code.BUSINESS_EXCEPTION, "业务异常", null);
    }

    public static <T> Res<T> unknownException() {
        return Res.build(Code.UNKNOWN_EXCEPTION, "未知异常", null);
    }

}