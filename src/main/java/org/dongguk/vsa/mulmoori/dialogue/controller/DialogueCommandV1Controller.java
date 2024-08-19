package org.dongguk.vsa.mulmoori.dialogue.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.annotation.security.AccountID;
import org.dongguk.vsa.mulmoori.core.dto.ResponseDto;
import org.dongguk.vsa.mulmoori.dialogue.dto.request.CreateDialogueRequestDto;
import org.dongguk.vsa.mulmoori.dialogue.dto.request.UpdateAnswerInDialogueRequestDto;
import org.dongguk.vsa.mulmoori.dialogue.usecase.CreateDialogueUseCase;
import org.dongguk.vsa.mulmoori.dialogue.usecase.UpdateAnswerInDialogueUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dialogues")
public class DialogueCommandV1Controller {

    private final CreateDialogueUseCase createDialogueUseCase;

    private final UpdateAnswerInDialogueUseCase updateAnswerInDialogueUseCase;

    @PostMapping("")
    public ResponseDto<?> createDialogue(
            @AccountID UUID accountId,
            @RequestBody @Valid CreateDialogueRequestDto requestDto
    ) {
        createDialogueUseCase.execute(accountId, requestDto);

        return ResponseDto.created(null);
    }

    @PutMapping("/{dialogueId}/answer")
    public ResponseDto<?> answerDialogue(
            @AccountID UUID accountId,
            @PathVariable Long dialogueId,
            @RequestBody @Valid UpdateAnswerInDialogueRequestDto requestDto
    ) {
        updateAnswerInDialogueUseCase.execute(accountId, dialogueId, requestDto);

        return ResponseDto.created(null);
    }
}
