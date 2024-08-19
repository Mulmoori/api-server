package org.dongguk.vsa.mulmoori.dialogue.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.dialogue.dto.request.UpdateAnswerInDialogueRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateAnswerInDialogueUseCase {

    void execute(
            UUID accountId,
            Long dialogueId,
            UpdateAnswerInDialogueRequestDto requestDto
    );
}
