package com.hao.base.common.login;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;

public class ClientTokenUtil {

    public static final byte[] bytes = "jwt".getBytes();

    public static String generate(Long userId) {
        return JWT.create()
                .setSubject(String.valueOf(userId))
                .setKey(bytes)
                .sign();
    }

    public static String generate(Long userId, int minutes) {
        return JWT.create()
                // 设置签发时间
                .setExpiresAt(DateUtil.offsetMinute(DateUtil.date(), minutes))
                .setSubject(String.valueOf(userId))
                .setKey(bytes)
                .sign();
    }

    public static boolean validate(String token) {
        if (!JWTUtil.verify(token, bytes)) {
            return false;
        }
        try {
            JWTValidator.of(token).validateDate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getSub(String token) {
        return JWTUtil.parseToken(token).getPayload("sub").toString();
    }

    public static void main(String[] args) {

    }
}
