package com.hao.base.common.login;

import com.hao.base.common.pojo.wrapper.Res;
import com.hao.base.common.util.simple.AjaxUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public abstract class LoginInterceptorAdapter<ID> implements HandlerInterceptor {

    public abstract ID tokenToId(String token);

    public abstract void onSuccess(ID id);

    public abstract void onExit();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 对 option 请求放行，用于 ajax 跨域
        String method = request.getMethod();
        if (HttpMethod.OPTIONS.name().equals(method)) {
            return true;
        }

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        ID userId = tokenToId(token);

        if (userId == null) {
            Res<String> unauthorized = Res.unauthorized();
            AjaxUtil.writeObject(response, 200, unauthorized);
            return false;
        }

        onSuccess(userId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        onExit();
    }
}
