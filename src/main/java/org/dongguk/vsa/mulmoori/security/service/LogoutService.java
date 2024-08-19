package org.dongguk.vsa.mulmoori.security.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.security.dto.info.CustomUserPrincipal;
import org.dongguk.vsa.mulmoori.security.repository.redis.RefreshTokenRepository;
import org.dongguk.vsa.mulmoori.security.usecase.LogoutUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutUseCase {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void execute(CustomUserPrincipal principal) {
        UUID accountId = principal.getId();

        refreshTokenRepository.deleteById(accountId);
    }
}
