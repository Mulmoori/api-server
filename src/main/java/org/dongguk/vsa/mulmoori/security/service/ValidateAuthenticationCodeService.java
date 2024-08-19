package org.dongguk.vsa.mulmoori.security.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.core.utility.JsonWebTokenUtil;
import org.dongguk.vsa.mulmoori.security.domain.redis.AuthenticationCode;
import org.dongguk.vsa.mulmoori.security.domain.redis.AuthenticationCodeHistory;
import org.dongguk.vsa.mulmoori.security.domain.redis.TemporaryToken;
import org.dongguk.vsa.mulmoori.security.dto.request.ValidateAuthenticationCodeRequestDto;
import org.dongguk.vsa.mulmoori.security.dto.response.TemporaryJsonWebTokenDto;
import org.dongguk.vsa.mulmoori.security.repository.redis.AuthenticationCodeHistoryRepository;
import org.dongguk.vsa.mulmoori.security.repository.redis.AuthenticationCodeRepository;
import org.dongguk.vsa.mulmoori.security.repository.redis.TemporaryTokenRepository;
import org.dongguk.vsa.mulmoori.security.usecase.ValidateAuthenticationCodeUseCase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateAuthenticationCodeService implements ValidateAuthenticationCodeUseCase {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final AuthenticationCodeHistoryRepository authenticationCodeHistoryRepository;
    private final TemporaryTokenRepository temporaryTokenRepository;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    public TemporaryJsonWebTokenDto execute(ValidateAuthenticationCodeRequestDto requestDto) {
        AuthenticationCode authenticationCode = authenticationCodeRepository.findById(requestDto.email())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        AuthenticationCodeHistory authenticationCodeHistory = authenticationCodeHistoryRepository.findById(requestDto.email())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        if (!bCryptPasswordEncoder.matches(requestDto.authenticationCode(), authenticationCode.getValue())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        authenticationCodeRepository.delete(authenticationCode);
        authenticationCodeHistoryRepository.delete(authenticationCodeHistory);

        TemporaryJsonWebTokenDto temporaryTokenDto = jsonWebTokenUtil.generateTemporaryJsonWebToken(requestDto.email());

        temporaryTokenRepository.save(TemporaryToken.builder()
                .email(requestDto.email())
                .value(temporaryTokenDto.getTemporaryToken())
                .build()
        );

        return temporaryTokenDto;
    }
}
