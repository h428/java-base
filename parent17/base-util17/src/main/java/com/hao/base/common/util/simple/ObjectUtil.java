package com.hao.base.common.util.simple;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 对象工具类，通过反射打印实体和生成实例
 */
public class ObjectUtil {

    // 不想随机生成值的列，比如 deleteTime, 外键等，因为这些列不能生成随机值，需要设置为 null
    private static final Set<String> EXCLUDE_FIELD = new HashSet<>();

    static {
        //        EXCLUDE_FIELD.add("constant");
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return "null object";
        }

        try {
            // 首先获取对象的所有域，并确定他们的名称
            Class<?> objClass = obj.getClass();
            Field[] fields = objClass.getDeclaredFields();

            if (objClass == String.class) {
                return (String)obj;
            }

            // 添加类名和左括号
            StringBuilder sb = new StringBuilder();
            sb.append(objClass.getSimpleName()).append("{");
            // 取出域，拼接起来
            for (Field field : fields) {
                String fieldName = field.getName();
                field.setAccessible(true);
                sb.append(fieldName).append("=").append(field.get(obj)).append(", ");
            }
            // 删除最后两个字符并打上右括号
            sb.delete(sb.length() - 2, sb.length());
            sb.append("}");
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toString(Collection<?> objs) {

        if (objs == null) {
            return "[null collection]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("the size is ").append(objs.size()).append(", data is [\n");

        for (Object obj : objs) {
            sb.append(toString(obj)).append("\n");
        }
        return sb.append("]").toString();
    }

    public static <K, V> String toString(Map<K, V> map) {

        if (map == null) {
            return "{null map}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("the size is ").append(map.size()).append(", data is {\n");

        for (Map.Entry<K, V> entry : map.entrySet()) {
            sb.append("{ key = ").append(entry.getKey()).append(", value = ").append(entry.getValue()).append(" }\n");
        }
        return sb.append("}").toString();
    }

    public static <T> T randomInstance(Class<T> clazz) {
        int num = (int)(Math.random() * 100); // 随机数
        return randomInstance(clazz, num);
    }

    /**
     * 利用反射技术，随机生成对象实例，其中 Id 列不设置，String 类型的列设置：为列名+随机数，整型直接设置为随机数
     *
     * @param clazz 目标类型的 Class
     * @param <T>   泛型 T，即目标对象类型
     * @return 返回生成的实例
     */
    public static <T> T randomInstance(Class<T> clazz, int num) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();

            Field[] declaredFields = clazz.getDeclaredFields();

            for (Field field : declaredFields) {

                String fieldName = field.getName();
                Class<?> fieldType = field.getType();

                // serialVersionUID 列跳过生成值
                if (field.getName().equals("serialVersionUID") || EXCLUDE_FIELD.contains(fieldName)) {
                    continue;
                }

                // 跳过 static 域
                int modifiers = field.getModifiers();
                if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
                    continue;
                }

                field.setAccessible(true);

                // 其他列，若是 String 类型，则根据 列名+随机数 进行赋值
                if (fieldType == String.class) {
                    // 字符串类型设置为：列名+随机数
                    field.set(obj, field.getName() + num);
                    continue;
                }

                // 若是时间相关的类则设置为当前时间
                if (fieldType == LocalDateTime.class) {
                    field.set(obj, LocalDateTime.now());
                    continue;
                }

                if (fieldType == LocalDate.class) {
                    field.set(obj, LocalDate.now());
                    continue;
                }

                if (fieldType == Date.class) {
                    field.set(obj, new Date());
                    continue;
                }

                if (fieldType == int.class || fieldType == Integer.class) {
                    if (fieldName.contains("status") || fieldName.contains("type")) {
                        // 名称包含 status 或是 type，数据库估计是 byte 类型，取模 128
                        field.set(obj, ((int)num) % 128);
                    } else {
                        // 若是整型，直接赋值
                        field.set(obj, num);
                    }
                    continue;
                }

                if (fieldType == long.class || fieldType == Long.class) {
                    field.set(obj, (long)num);
                    continue;
                }

                if (fieldType == double.class || fieldType == Double.class) {
                    field.set(obj, (double)num);
                    continue;
                }

                if (fieldType == float.class || fieldType == Float.class) {
                    field.set(obj, (float)num);
                    continue;
                }

                if (fieldType == boolean.class || fieldType == Boolean.class) {
                    field.set(obj, num % 2 == 0);
                    continue;
                }

                if (field.getType() == Date.class) {
                    field.set(obj, new Date());
                    continue;
                }

            }
            // 返回生成的对象
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}