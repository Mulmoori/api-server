package org.dongguk.vsa.mulmoori.core.exception.type;

import lombok.Getter;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.springframework.messaging.MessageDeliveryException;

@Getter
public class StompSecurityException extends MessageDeliveryException {

    private final ErrorCode errorCode;

    public StompSecurityException(ErrorCode errorCode) {
        super(String.format("%d|%s", errorCode.getCode(), errorCode.getMessage()));

        this.errorCode = errorCode;
    }
}
