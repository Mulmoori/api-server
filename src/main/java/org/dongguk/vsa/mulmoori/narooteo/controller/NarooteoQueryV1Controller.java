package org.dongguk.vsa.mulmoori.narooteo.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.annotation.security.AccountID;
import org.dongguk.vsa.mulmoori.core.dto.ResponseDto;
import org.dongguk.vsa.mulmoori.narooteo.usecase.ReadNarooteoBriefUseCase;
import org.dongguk.vsa.mulmoori.narooteo.usecase.ReadNarooteoSummaryUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/narooteos")
public class NarooteoQueryV1Controller {

    private final ReadNarooteoBriefUseCase readNarooteoBriefUseCase;
    private final ReadNarooteoSummaryUseCase readNarooteoSummaryUseCase;

    @GetMapping("/briefs")
    public ResponseDto<?> readNarooteoBriefs(
            @RequestParam("participationCode") String participationCode
    ) {
        return ResponseDto.ok(readNarooteoBriefUseCase.execute(participationCode));
    }

    @GetMapping("/{narooteoId}/summaries")
    public ResponseDto<?> readNarooteoSummaries(
            @AccountID UUID accountId,
            @PathVariable Long narooteoId
    ) {
        return ResponseDto.ok(readNarooteoSummaryUseCase.execute(accountId, narooteoId));
    }
}
