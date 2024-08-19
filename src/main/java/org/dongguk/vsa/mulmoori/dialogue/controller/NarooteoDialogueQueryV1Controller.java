package org.dongguk.vsa.mulmoori.dialogue.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.annotation.security.AccountID;
import org.dongguk.vsa.mulmoori.core.dto.ResponseDto;
import org.dongguk.vsa.mulmoori.dialogue.usecase.ReadDialogueOverviewListUseCase;
import org.dongguk.vsa.mulmoori.narooteo.usecase.ReadNarooteoOverviewListUsingUserUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/narooteos")
public class NarooteoDialogueQueryV1Controller {

    private final ReadDialogueOverviewListUseCase readDialogueOverviewListUseCase;
    private final ReadNarooteoOverviewListUsingUserUseCase readNarooteoOverviewListUsingUserUseCase;

    @GetMapping("/{narooteoId}/dialogues")
    public ResponseDto<?> getNarooteoDialogues(
            @AccountID UUID accountId,
            @PathVariable Long narooteoId
    ) {
        return ResponseDto.ok(readDialogueOverviewListUseCase.execute(accountId, narooteoId));
    }

    @GetMapping("/{narooteoId}/users/dialogues")
    public ResponseDto<?> getNarooteoDialoguesUsingUser(
            @AccountID UUID accountId,
            @PathVariable Long narooteoId
    ) {
        return ResponseDto.ok(readNarooteoOverviewListUsingUserUseCase.execute(accountId));
    }
}
