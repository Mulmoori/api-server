package org.dongguk.vsa.mulmoori.security.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.contants.Constants;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.core.utility.JsonWebTokenUtil;
import org.dongguk.vsa.mulmoori.security.domain.mysql.Account;
import org.dongguk.vsa.mulmoori.security.domain.redis.RefreshToken;
import org.dongguk.vsa.mulmoori.security.domain.redis.TemporaryToken;
import org.dongguk.vsa.mulmoori.security.domain.type.ESecurityProvider;
import org.dongguk.vsa.mulmoori.security.domain.type.ESecurityRole;
import org.dongguk.vsa.mulmoori.security.dto.request.SignUpByDefaultRequestDto;
import org.dongguk.vsa.mulmoori.security.dto.response.DefaultJsonWebTokenDto;
import org.dongguk.vsa.mulmoori.security.repository.mysql.AccountRepository;
import org.dongguk.vsa.mulmoori.security.repository.redis.RefreshTokenRepository;
import org.dongguk.vsa.mulmoori.security.repository.redis.TemporaryTokenRepository;
import org.dongguk.vsa.mulmoori.security.usecase.SignUpByDefaultUseCase;
import org.dongguk.vsa.mulmoori.user.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpByDefaultService implements SignUpByDefaultUseCase {

    private final AccountRepository accountRepository;

    private final TemporaryTokenRepository temporaryTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    public DefaultJsonWebTokenDto execute(String temporaryToken, SignUpByDefaultRequestDto requestDto) {
        // Temporary Token 검증
        Claims claims = jsonWebTokenUtil.validateToken(temporaryToken);

        // Email 추출
        String email = claims.get(Constants.ACCOUNT_ID_CLAIM_NAME, String.class);

        // Temporary Token 존재 여부 확인
        if (!isEqualsTemporaryToken(email, temporaryToken)) {
            throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);
        }

        // 이메일 중복 검사
        if (isDuplicatedEmail(email)) {
            throw new CommonException(ErrorCode.DUPLICATED_RESOURCE);
        }

        // 유저 생성 및 저장
        Account account = User.builder()
                .provider(ESecurityProvider.DEFAULT)
                .serialId(email)
                .password(bCryptPasswordEncoder.encode(requestDto.password()))
                .nickname(requestDto.nickname())
                .build();


        accountRepository.save(account);

        // Default Json Web Token 생성
        DefaultJsonWebTokenDto defaultJsonWebTokenDto = jsonWebTokenUtil.generateDefaultJsonWebTokens(
                account.getId(),
                ESecurityRole.USER
        );

        // Refresh Token 저장
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .accountId(account.getId())
                        .value(defaultJsonWebTokenDto.getRefreshToken())
                        .build()
        );

        // Temporary Token 삭제
        temporaryTokenRepository.deleteById(email);

        return defaultJsonWebTokenDto;
    }

    /**
     * Temporary Token 일치 여부 확인
     * @param email 이메일
     * @param temporaryToken Temporary Token
     * @return Redis에 저장된 Temporary Token과 일치 여부
     */
    private Boolean isEqualsTemporaryToken(String email, String temporaryToken) {
        if (email == null) {
            return false;
        }

        TemporaryToken temporaryTokenEntity = temporaryTokenRepository.findById(email)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));

        return temporaryTokenEntity.getValue().equals(temporaryToken);
    }

    /**
     * 이메일 중복 검사
     * @param email 이메일
     * @return 중복 여부
     */
    private Boolean isDuplicatedEmail(String email) {
        return accountRepository.findBySerialId(email).isPresent();
    }
}
