package org.dongguk.vsa.mulmoori.security.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.security.dto.response.DefaultJsonWebTokenDto;

@UseCase
public interface ReissueJsonWebTokenUseCase {

    /**
     * Refresh Token을 이용하여 새로운 JsonWebToken을 발급하는 유스케이스
     * @param refreshToken Refresh Token
     * @return DefaultJsonWebTokenDto
     */
    DefaultJsonWebTokenDto execute(String refreshToken);
}
