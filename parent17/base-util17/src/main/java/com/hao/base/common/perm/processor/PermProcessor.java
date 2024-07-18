package com.hao.base.common.perm.processor;

import cn.hutool.core.util.StrUtil;
import com.hao.base.common.exception.NoPermissionException;
import com.hao.base.common.perm.anno.Perm;
import com.hao.base.common.util.runtime.SpringContextUtil;
import java.lang.reflect.Method;

public class PermProcessor extends CommonProcessor<Perm> {

    private final ThreadPermApi threadPermApi = SpringContextUtil.getBean(ThreadPermApi.class);

    @Override
    protected void check(Method method) {
        Perm annotation = method.getAnnotation(super.annotationClass);
        String perm = annotation.value();

        if (StrUtil.isBlank(perm)) {
            return;
        }

        if (!threadPermApi.queryPerms().contains(perm)) {
            throw new NoPermissionException();
        }
    }
}
