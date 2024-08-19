package org.dongguk.vsa.mulmoori.user.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.user.dto.request.CreateUserNarooteoRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserNarooteoUseCase {
    void execute(UUID accountId, CreateUserNarooteoRequestDto requestDto);
}
