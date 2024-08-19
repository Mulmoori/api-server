package org.dongguk.vsa.mulmoori.security.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.security.domain.mysql.Account;
import org.dongguk.vsa.mulmoori.security.repository.mysql.AccountRepository;
import org.dongguk.vsa.mulmoori.security.repository.redis.RefreshTokenRepository;
import org.dongguk.vsa.mulmoori.security.usecase.WithdrawalUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WithdrawalService implements WithdrawalUseCase {

    private final AccountRepository accountRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void execute(UUID accountId) {
        // 계정 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        // 계정 삭제
        accountRepository.delete(account);

        // RefreshToken 삭제
        refreshTokenRepository.deleteById(accountId);
    }
}
