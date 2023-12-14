package com.hao.base.common.util;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ClassUtil {

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void setField(Class<T> clazz, T entity, String fieldName, Object value) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(entity, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void setField(T entity, String fieldName, Object value) {

        if (entity == null) {
            return;
        }

        try {
            Class<?> clazz = entity.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(entity, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Object getField(T entity, String fieldName){

        if (entity == null) {
            return null;
        }

        try {
            Class<?> clazz = entity.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(entity);
        } catch (NoSuchFieldException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> convertToMap(Object entity) {

        if (entity == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        try {
            Class<?> clazz = entity.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();

            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(entity));
            }

            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
