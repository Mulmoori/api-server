package org.dongguk.vsa.mulmoori.security.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.security.domain.redis.RefreshToken;
import org.dongguk.vsa.mulmoori.security.dto.info.CustomUserPrincipal;
import org.dongguk.vsa.mulmoori.security.dto.response.DefaultJsonWebTokenDto;
import org.dongguk.vsa.mulmoori.security.repository.redis.RefreshTokenRepository;
import org.dongguk.vsa.mulmoori.security.usecase.LoginByDefaultUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginByDefaultService implements LoginByDefaultUseCase {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void execute(CustomUserPrincipal principal, DefaultJsonWebTokenDto jsonWebTokenDto) {
        UUID userId = principal.getId();
        String refreshToken = jsonWebTokenDto.getRefreshToken();

        if (refreshToken != null) {
            refreshTokenRepository.save(
                    RefreshToken.builder()
                            .accountId(userId)
                            .value(refreshToken)
                            .build()
            );
        }
    }
}
