package org.dongguk.vsa.mulmoori.security.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.contants.Constants;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.core.utility.JsonWebTokenUtil;
import org.dongguk.vsa.mulmoori.security.domain.mysql.Account;
import org.dongguk.vsa.mulmoori.security.domain.redis.RefreshToken;
import org.dongguk.vsa.mulmoori.security.dto.response.DefaultJsonWebTokenDto;
import org.dongguk.vsa.mulmoori.security.repository.mysql.AccountRepository;
import org.dongguk.vsa.mulmoori.security.repository.redis.RefreshTokenRepository;
import org.dongguk.vsa.mulmoori.security.usecase.ReissueJsonWebTokenUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReissueJsonWebTokenService implements ReissueJsonWebTokenUseCase {

    private final AccountRepository accountRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    public DefaultJsonWebTokenDto execute(String refreshToken) {
        // Refresh Token 검증
        Claims claims = jsonWebTokenUtil.validateToken(refreshToken);

        // Account ID 추출
        UUID accountId = UUID.fromString(claims.get(Constants.ACCOUNT_ID_CLAIM_NAME, String.class));

        // Token 일치 여부 확인
        if (!isEqualsRefreshToken(accountId, refreshToken)) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));

        // Default Json Web Token 생성
        DefaultJsonWebTokenDto defaultJsonWebTokenDto = jsonWebTokenUtil.generateDefaultJsonWebTokens(
                account.getId(),
                account.getRole()
        );

        // Refresh Token 갱신
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .accountId(account.getId())
                        .value(defaultJsonWebTokenDto.getRefreshToken())
                        .build()
        );

        return defaultJsonWebTokenDto;
    }

    /**
     * Refresh Token 일치 여부 확인
     * @param accountId Account ID
     * @param refreshToken Refresh Token
     * @return Redis에 저장된 Refresh Token과 일치 여부
     */
    private Boolean isEqualsRefreshToken(UUID accountId, String refreshToken) {
        RefreshToken token = refreshTokenRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));

        return token.getValue().equals(refreshToken);
    }
}
