package org.dongguk.vsa.mulmoori.narooteo.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.narooteo.dto.response.NarooteoSummaryDto;
import org.dongguk.vsa.mulmoori.narooteo.repository.NarooteoRepository;
import org.dongguk.vsa.mulmoori.narooteo.usecase.ReadNarooteoSummaryUseCase;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.dongguk.vsa.mulmoori.user.domain.mysql.UserNarooteo;
import org.dongguk.vsa.mulmoori.user.domain.type.ENarooteoRole;
import org.dongguk.vsa.mulmoori.user.repository.UserNarooteoRepository;
import org.dongguk.vsa.mulmoori.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadNarooteoSummaryService implements ReadNarooteoSummaryUseCase {

    private final UserRepository userRepository;
    private final NarooteoRepository narooteoRepository;
    private final UserNarooteoRepository userNarooteoRepository;

    @Override
    public NarooteoSummaryDto execute(UUID accountId, Long narooteoId) {
        // 1. 사용자 및 나루터 조회
        User requestUser = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        Narooteo narooteo = narooteoRepository.findById(narooteoId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 2. 권한 확인
        UserNarooteo userNarooteo = userNarooteoRepository.findByUserAndNarooteo(requestUser, narooteo)
                .orElseThrow(() -> new CommonException(ErrorCode.ACCESS_DENIED));

        // 3. 호스트 유저 조회
        UserNarooteo hostNarooteo = userNarooteoRepository.findByNarooteoAndRole(narooteo, ENarooteoRole.HOST)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 4. 반환
        return NarooteoSummaryDto.fromEntity(narooteo, hostNarooteo.getUser(), requestUser);
    }
}
