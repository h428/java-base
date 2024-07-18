package com.hao.base.common.perm.aop;

import com.hao.base.common.perm.processor.CommonProcessor;
import com.hao.base.common.perm.processor.PermProcessor;
import com.hao.base.common.perm.processor.RoleProcessor;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.ResponseBody;

@Aspect
public class PermAop {

    // 匹配所有被@Perm注解标记的方法
    @Pointcut("@annotation(com.hao.base.common.perm.anno.Perm)")
    public void permAnnotatedMethods() {
    }

    // 匹配所有被@Role注解标记的方法
    @Pointcut("@annotation(com.hao.base.common.perm.anno.Role)")
    public void roleAnnotatedMethods() {
    }

    /**
     * 定义切面：com.lab.web.controller 包下的所有类的所有方法
     */
    @Pointcut("permAnnotatedMethods() || roleAnnotatedMethods()")
    public void aspect() {

    }

    // 组装需要处理的权限注解处理链
    // 有点类似职责链模式，但由于校验校验无需嵌套调用，只需用 list 简单组合即可
    static final CommonProcessor<?>[] processors = {
            new PermProcessor(),
            new RoleProcessor()
    };

    /**
     * 权限环绕通知
     */
    @ResponseBody
    @Around("aspect()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取 aop 拦截的方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();

        // CommonProcessor 提供模板方法
        for (CommonProcessor<?> processor : processors) {
            processor.process(targetMethod);
        }

        // 所有权限校验通过，放行，并拿到 Controller 方法的返回结果
        return joinPoint.proceed();
    }

}
