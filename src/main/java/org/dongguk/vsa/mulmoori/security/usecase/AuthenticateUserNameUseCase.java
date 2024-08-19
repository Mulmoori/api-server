package org.dongguk.vsa.mulmoori.security.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.springframework.security.core.userdetails.UserDetailsService;

@UseCase
public interface AuthenticateUserNameUseCase extends UserDetailsService {
}
