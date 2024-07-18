package com.hao.base.common.perm;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class PermModuleConfiguration implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 在这里实现对特定bean的增强或修改，基于pointcut参数
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

}
