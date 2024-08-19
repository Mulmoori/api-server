package org.dongguk.vsa.mulmoori.narooteo.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.narooteo.dto.response.NarooteoSummaryDto;

import java.util.UUID;

@UseCase
public interface ReadNarooteoSummaryUseCase {

    NarooteoSummaryDto execute(UUID accountId, Long narooteoId);
}
