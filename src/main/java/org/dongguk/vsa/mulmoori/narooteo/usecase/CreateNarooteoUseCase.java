package org.dongguk.vsa.mulmoori.narooteo.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.narooteo.dto.request.CreateNarooteoRequestDto;
import org.dongguk.vsa.mulmoori.narooteo.dto.response.NarooteoMiniDto;

import java.util.UUID;

@UseCase
public interface CreateNarooteoUseCase {

    /**
     * 나루터 생성
     * @param accountId 계정 ID
     * @param requestDto 요청 DTO
     * @return 나루터 Mini DTO
     */
    NarooteoMiniDto execute(UUID accountId, CreateNarooteoRequestDto requestDto);
}
