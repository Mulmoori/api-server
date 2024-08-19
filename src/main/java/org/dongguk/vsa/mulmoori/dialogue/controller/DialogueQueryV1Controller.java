package org.dongguk.vsa.mulmoori.dialogue.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.annotation.security.AccountID;
import org.dongguk.vsa.mulmoori.core.dto.ResponseDto;
import org.dongguk.vsa.mulmoori.dialogue.dto.request.UpdateAnswerInDialogueRequestDto;
import org.dongguk.vsa.mulmoori.dialogue.usecase.ReadDialogueDetailUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/dialogues")
public class DialogueQueryV1Controller {

    private final ReadDialogueDetailUseCase readDialogueDetailUseCase;

    @PostMapping("/{dialogueId}")
    public ResponseDto<?> readDialogueDetail(
            @AccountID UUID accountId,
            @PathVariable Long dialogueId
    ) {
        return ResponseDto.ok(readDialogueDetailUseCase.execute(accountId, dialogueId));
    }
}
