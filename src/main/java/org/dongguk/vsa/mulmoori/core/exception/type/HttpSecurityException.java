package org.dongguk.vsa.mulmoori.core.exception.type;

import io.jsonwebtoken.JwtException;
import lombok.Getter;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;

@Getter
public class HttpSecurityException extends JwtException {

    private final ErrorCode errorCode;

    public HttpSecurityException(String message, ErrorCode errorCode) {
        super(message);

        this.errorCode = errorCode;
    }
}
