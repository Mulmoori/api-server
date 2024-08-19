package org.dongguk.vsa.mulmoori.security.usecase;


import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.security.dto.info.CustomUserPrincipal;

import java.util.UUID;

@UseCase
public interface AuthenticateJsonWebTokenUseCase {

    CustomUserPrincipal execute(UUID accountId);
}
