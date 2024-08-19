package org.dongguk.vsa.mulmoori.dialogue.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.dialogue.dto.response.DialogueOverviewListDto;

import java.util.UUID;

@UseCase
public interface ReadDialogueOverviewListUsingUserUseCase {

    DialogueOverviewListDto execute(UUID accountId, Long narooteoId);
}
