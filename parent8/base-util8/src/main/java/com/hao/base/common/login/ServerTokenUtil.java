package com.hao.base.common.login;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.hao.base.common.pojo.constant.TimeConstant;
import com.hao.base.common.util.spring.RedisUtil;

public class ServerTokenUtil {

    /**
     * 简单地放置 token 双向结构
     *
     * @param prefix key 前缀
     * @param userId 用户 id
     * @param token  对应 token
     */
    private static void putToken(String prefix, Long userId, String token) {
        // 保存 token 到 redis
        String tokenKey = prefix + token;
        RedisUtil.put(tokenKey, String.valueOf(userId), TimeConstant.SECONDS_ONE_HOUR);
    }

    public static String generateLoginTokenAndPutToRedis(String prefix, Long userId) {
        Assert.notNull(userId);

        // 使用 UUID 作为 token
        String token = IdUtil.simpleUUID();

        // 随机生成 tokenKey 作为 token
        String tokenKey;
        do {
            tokenKey = prefix + token;
        } while (RedisUtil.exist(tokenKey));

        // 放置 token 结构
        putToken(prefix, userId, token);

        return token;
    }


    /**
     * 刷新 token 并返回 token 对应的用户 id，模拟 HttpSession
     *
     * @param token
     * @return 若 token 存在则刷新时间并返回 userId；不存在则返回 null 表示未登录
     */
    public static Long refresh(String prefix, String token) {

        String tokenKey = prefix + token;

        String userIdStr = RedisUtil.get(tokenKey);

        if (userIdStr == null) {
            return null;
        }

        // 放置 token 结构，使用原有 token 值即为刷新 token
        Long userId = Long.valueOf(userIdStr);
        putToken(prefix, userId, token);

        // 返回成功解析的 userId
        return userId;
    }

    /**
     * 根据 tokenType 和 token 移除 token
     *
     * @param prefix key 前缀
     * @param token
     */
    public static void removeToken(String prefix, String token) {
        String tokenKey = prefix + token;
        RedisUtil.remove(tokenKey);
    }

}
