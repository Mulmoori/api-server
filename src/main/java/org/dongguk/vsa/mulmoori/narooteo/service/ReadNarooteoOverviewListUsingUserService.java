package org.dongguk.vsa.mulmoori.narooteo.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.narooteo.dto.response.NarooteoOverviewListDto;
import org.dongguk.vsa.mulmoori.narooteo.repository.NarooteoRepository;
import org.dongguk.vsa.mulmoori.narooteo.usecase.ReadNarooteoOverviewListUsingUserUseCase;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.dongguk.vsa.mulmoori.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadNarooteoOverviewListUsingUserService implements ReadNarooteoOverviewListUsingUserUseCase {

    private final UserRepository userRepository;
    private final NarooteoRepository narooteoRepository;

    @Override
    public NarooteoOverviewListDto execute(UUID accountId) {
        // 1. 사용자 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        // 2. 해당 사용자의 나루터 목록 조회
        List<Narooteo> narooteos = narooteoRepository.findAllByUser(user);

        // 3. 현재 Host가 Stomp 연결 중인지 조회
        Map<Long, Boolean> isConnectionHostMap = new HashMap<>();

        // 4. 각 나루터의 참여자 수 조회
        Map<Long, Integer> participantCountMap = new HashMap<>();

        // 5. DTO 변환
        return NarooteoOverviewListDto.fromEntities(
                narooteos,
                isConnectionHostMap,
                participantCountMap
        );
    }
}
