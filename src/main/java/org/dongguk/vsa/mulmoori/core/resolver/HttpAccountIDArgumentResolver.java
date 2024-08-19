package org.dongguk.vsa.mulmoori.core.resolver;

import org.dongguk.vsa.mulmoori.core.annotation.security.AccountID;
import org.dongguk.vsa.mulmoori.core.contants.Constants;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

@Component
public class HttpAccountIDArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UUID.class)
                && parameter.hasParameterAnnotation(AccountID.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        String userAccountId = (String) webRequest.getAttribute(
                Constants.ACCOUNT_ID_ATTRIBUTE_NAME,
                WebRequest.SCOPE_REQUEST
        );

        if (userAccountId == null) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        return UUID.fromString(userAccountId);
    }
}
