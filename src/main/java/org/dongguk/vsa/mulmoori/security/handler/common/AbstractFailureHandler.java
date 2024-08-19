package org.dongguk.vsa.mulmoori.security.handler.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.dongguk.vsa.mulmoori.core.dto.ExceptionDto;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFailureHandler {

    private static final String CONTENT_TYPE = "application/json";
    private static final String CHARACTER_ENCODING = "UTF-8";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    protected void setErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode
    ) throws IOException {
        setResponseHeader(response, errorCode);

        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("data", null);
        result.put("error", ExceptionDto.of(errorCode));

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    protected void setErrorResponse(
            HttpServletResponse response,
            String message,
            ErrorCode errorCode
    ) throws IOException {
        setResponseHeader(response, errorCode);

        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("data", null);
        result.put("error", ExceptionDto.of(errorCode, message));

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    private void setResponseHeader(
            HttpServletResponse response,
            ErrorCode errorCode
    ) {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        response.setStatus(errorCode.getHttpStatus().value());
    }
}
