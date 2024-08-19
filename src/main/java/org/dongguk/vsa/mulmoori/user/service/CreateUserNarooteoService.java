package org.dongguk.vsa.mulmoori.user.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.narooteo.repository.NarooteoRepository;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.dongguk.vsa.mulmoori.user.domain.mysql.UserNarooteo;
import org.dongguk.vsa.mulmoori.user.domain.type.ENarooteoRole;
import org.dongguk.vsa.mulmoori.user.dto.request.CreateUserNarooteoRequestDto;
import org.dongguk.vsa.mulmoori.user.repository.UserNarooteoRepository;
import org.dongguk.vsa.mulmoori.user.repository.UserRepository;
import org.dongguk.vsa.mulmoori.user.usecase.CreateUserNarooteoUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserNarooteoService implements CreateUserNarooteoUseCase {

    private final UserRepository userRepository;
    private final NarooteoRepository narooteoRepository;
    private final UserNarooteoRepository userNarooteoRepository;

    @Override
    public void execute(UUID accountId, CreateUserNarooteoRequestDto requestDto) {
        // 1. 사용자 및 나루터 조회
        User requestUser = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        Narooteo narooteo = narooteoRepository.findById(requestDto.narooteoId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 2. 권한 확인
        userNarooteoRepository.findByUserAndNarooteo(requestUser, narooteo)
                .orElseThrow(() -> new CommonException(ErrorCode.DUPLICATED_RESOURCE));

        // 3. 나루터 참여자 추가
        userNarooteoRepository.save(
                UserNarooteo.builder()
                        .user(requestUser)
                        .narooteo(narooteo)
                        .role(ENarooteoRole.PARTICIPANT)
                        .build()
        );
    }
}
