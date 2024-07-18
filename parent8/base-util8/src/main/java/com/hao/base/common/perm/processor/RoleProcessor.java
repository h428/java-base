package com.hao.base.common.perm.processor;

import cn.hutool.core.util.StrUtil;
import com.hao.base.common.exception.NoPermissionException;
import com.hao.base.common.perm.anno.Role;
import com.hao.base.common.util.spring.SpringContextUtil;
import java.lang.reflect.Method;

public class RoleProcessor extends CommonProcessor<Role> {

    private final ThreadPermApi threadPermApi = SpringContextUtil.getBean(ThreadPermApi.class);

    @Override
    protected void check(Method method) {
        Role annotation = method.getAnnotation(super.annotationClass);
        String role = annotation.value();

        if (StrUtil.isBlank(role)) {
            return;
        }

        if (!threadPermApi.queryRoles().contains(role)) {
            throw new NoPermissionException();
        }
    }
}
