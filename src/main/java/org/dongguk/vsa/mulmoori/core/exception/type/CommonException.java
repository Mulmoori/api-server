package org.dongguk.vsa.mulmoori.core.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;

@Getter
@RequiredArgsConstructor
public class CommonException extends RuntimeException {

    private final ErrorCode errorCode;

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}
