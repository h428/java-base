package com.hao.base.common.util.simple;

import cn.hutool.core.util.ClassUtil;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

public class CrudUtil {

    @SuppressWarnings("unchecked")
    public static <T> T getId(Object entity) {
        assert entity != null;
        try {
            Class<?> clazz = entity.getClass();
            Method getId = ClassUtil.getPublicMethod(clazz, "getId");
            return (T)getId.invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static <T> void setTime(T entity, LocalDateTime time) {

        if (entity == null) {
            return;
        }

        try {
            Class<?> clazz = entity.getClass();

            Object id = getId(entity);

            if (id == null) {
                // 是 insert
                Method setCreateTime = ClassUtil.getPublicMethod(clazz, "setCreateTime", LocalDateTime.class);
                setCreateTime.invoke(entity, time);
            }

            // 不管是 insert 还是 update 一定都会更新 updateTime
            Method setUpdateTime = ClassUtil.getPublicMethod(clazz, "setUpdateTime", LocalDateTime.class);
            setUpdateTime.invoke(entity, time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
