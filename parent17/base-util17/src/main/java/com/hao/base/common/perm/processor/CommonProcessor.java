package com.hao.base.common.perm.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public abstract class CommonProcessor<T extends Annotation> {

    // 参数化类型参数：即子类的注解类型
    protected Class<T> annotationClass;

    @SuppressWarnings("unchecked")
    public CommonProcessor() {
        // 获取父类类型并转化为参数化类型 ParameterizedType 以获取泛型信息
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        // 使用参数化类型获取泛型的实际信息
        this.annotationClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    protected abstract void check(Method method);

    public final void process(Method method) {
        // 若指定方法没有添加注解，直接返回不做后续校验
        if (!method.isAnnotationPresent(annotationClass)) {
            return;
        }

        // 执行子类的校验
        check(method);
    }

}
