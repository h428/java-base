package com.hao.base.common.login;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServerTokenType {

    private String prefix;

    /**
     * 执行登录并返回 token
     *
     * @param userId baseUserId
     * @return token
     */
    public String userLogin(Long userId) {
        return ServerTokenUtil.generateLoginTokenAndPutToRedis(prefix, userId);
    }

    public Long userId(String token) {
        return ServerTokenUtil.refresh(prefix, token);
    }

    /**
     * 根据 token 判断是否已登录
     *
     * @param token 待判断的 token
     * @return 已登录则返回对应的 baseUserId；否则返回 null
     */
    public boolean hasLogin(String token) {
        return ServerTokenUtil.refresh(prefix, token) != null;
    }

    public void logout(String token) {
        ServerTokenUtil.removeToken(prefix, token);
    }

}
