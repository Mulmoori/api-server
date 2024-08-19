package org.dongguk.vsa.mulmoori.narooteo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.annotation.security.AccountID;
import org.dongguk.vsa.mulmoori.core.dto.ResponseDto;
import org.dongguk.vsa.mulmoori.narooteo.dto.request.CreateNarooteoRequestDto;
import org.dongguk.vsa.mulmoori.narooteo.usecase.CreateNarooteoUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/narooteos")
public class NarooteoCommandV1Controller {

    private final CreateNarooteoUseCase createNarooteoUseCase;

    @PostMapping("")
    public ResponseDto<?> createNarooteo(
            @AccountID UUID accountId,
            @RequestBody @Valid CreateNarooteoRequestDto requestDto
    ) {
        return ResponseDto.created(createNarooteoUseCase.execute(accountId, requestDto));
    }
}
