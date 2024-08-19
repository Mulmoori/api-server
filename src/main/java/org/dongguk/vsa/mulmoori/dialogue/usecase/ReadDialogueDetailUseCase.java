package org.dongguk.vsa.mulmoori.dialogue.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.dialogue.dto.response.DialogueDetailDto;

import java.util.UUID;

@UseCase
public interface ReadDialogueDetailUseCase {

    DialogueDetailDto execute(UUID accountId, Long dialogueId);
}
