package org.dongguk.vsa.mulmoori.core.interceptor.pre;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dongguk.vsa.mulmoori.core.contants.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpAccountIDInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        request.setAttribute(Constants.ACCOUNT_ID_ATTRIBUTE_NAME, authentication.getName());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
