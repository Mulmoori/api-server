package org.dongguk.vsa.mulmoori.security.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.security.dto.info.CustomUserPrincipal;

@UseCase
public interface LogoutUseCase {

    /**
     * Security 단에서 사용되는 Logout 유스케이스
     * @param principal UserPrincipal
     */
    void execute(CustomUserPrincipal principal);
}
