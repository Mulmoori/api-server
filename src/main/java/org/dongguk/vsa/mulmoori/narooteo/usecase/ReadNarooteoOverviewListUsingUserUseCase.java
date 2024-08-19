package org.dongguk.vsa.mulmoori.narooteo.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.narooteo.dto.response.NarooteoOverviewListDto;

import java.util.UUID;

@UseCase
public interface ReadNarooteoOverviewListUsingUserUseCase {

    NarooteoOverviewListDto execute(UUID accountId);
}
