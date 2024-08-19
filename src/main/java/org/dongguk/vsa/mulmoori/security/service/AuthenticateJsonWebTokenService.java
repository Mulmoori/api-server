package org.dongguk.vsa.mulmoori.security.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.security.domain.mysql.Account;
import org.dongguk.vsa.mulmoori.security.dto.info.CustomUserPrincipal;
import org.dongguk.vsa.mulmoori.security.repository.mysql.AccountRepository;
import org.dongguk.vsa.mulmoori.security.repository.redis.RefreshTokenRepository;
import org.dongguk.vsa.mulmoori.security.usecase.AuthenticateJsonWebTokenUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticateJsonWebTokenService implements AuthenticateJsonWebTokenUseCase {

    private final AccountRepository accountRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public CustomUserPrincipal execute(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        refreshTokenRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_LOGIN_USER));

        return CustomUserPrincipal.create(account);
    }
}
