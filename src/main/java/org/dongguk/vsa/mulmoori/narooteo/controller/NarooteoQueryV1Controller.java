package org.dongguk.vsa.mulmoori.narooteo.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.dto.ResponseDto;
import org.dongguk.vsa.mulmoori.narooteo.usecase.ReadNarooteoBriefUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/narooteos")
public class NarooteoQueryV1Controller {

    private final ReadNarooteoBriefUseCase readNarooteoBriefUseCase;

    @GetMapping("/briefs")
    public ResponseDto<?> readNarooteoBriefs(
            @RequestParam("participationCode") String participationCode
    ) {

    }
}
