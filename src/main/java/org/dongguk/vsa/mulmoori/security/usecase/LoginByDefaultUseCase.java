package org.dongguk.vsa.mulmoori.security.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.security.dto.info.CustomUserPrincipal;
import org.dongguk.vsa.mulmoori.security.dto.response.DefaultJsonWebTokenDto;

@UseCase
public interface LoginByDefaultUseCase {

    /**
     * Security에서 사용되는 Login 유스케이스
     * @param principal UserPrincipal
     * @param jsonWebTokenDto DefaultJsonWebTokenDto
     */
    void execute(CustomUserPrincipal principal, DefaultJsonWebTokenDto jsonWebTokenDto);
}
