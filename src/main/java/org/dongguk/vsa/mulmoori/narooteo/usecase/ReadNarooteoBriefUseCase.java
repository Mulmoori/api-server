package org.dongguk.vsa.mulmoori.narooteo.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.narooteo.dto.request.CreateNarooteoRequestDto;
import org.dongguk.vsa.mulmoori.narooteo.dto.response.NarooteoBriefDto;
import org.dongguk.vsa.mulmoori.narooteo.dto.response.NarooteoMiniDto;

import java.util.UUID;

@UseCase
public interface ReadNarooteoBriefUseCase {

    /**
     * 나루터 간략 DTO를 조회합니다.
     *
     * @param participationCode 참여 코드
     *
     * @return 나루터 간략 DTO
     */
    NarooteoBriefDto execute(String participationCode);
}
