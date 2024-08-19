package org.dongguk.vsa.mulmoori.dialogue.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.dialogue.dto.request.CreateDialogueRequestDto;

import java.util.UUID;

@UseCase
public interface CreateDialogueUseCase {

    void execute(UUID accountId, CreateDialogueRequestDto requestDto);
}
