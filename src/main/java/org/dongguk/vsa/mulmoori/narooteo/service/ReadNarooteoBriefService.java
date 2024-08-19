package org.dongguk.vsa.mulmoori.narooteo.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.narooteo.dto.response.NarooteoBriefDto;
import org.dongguk.vsa.mulmoori.narooteo.repository.NarooteoRepository;
import org.dongguk.vsa.mulmoori.narooteo.usecase.ReadNarooteoBriefUseCase;
import org.dongguk.vsa.mulmoori.user.domain.mysql.UserNarooteo;
import org.dongguk.vsa.mulmoori.user.domain.type.ENarooteoRole;
import org.dongguk.vsa.mulmoori.user.repository.UserNarooteoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadNarooteoBriefService implements ReadNarooteoBriefUseCase {

    private final NarooteoRepository narooteoRepository;
    private final UserNarooteoRepository userNarooteoRepository;

    @Override
    public NarooteoBriefDto execute(String participationCode) {
        Narooteo narooteo = narooteoRepository.findByParticipationCode(participationCode)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        UserNarooteo userNarooteo = userNarooteoRepository.findByNarooteoAndRole(narooteo, ENarooteoRole.HOST)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return NarooteoBriefDto.fromEntity(narooteo, userNarooteo.getUser());
    }
}
