package org.dongguk.vsa.mulmoori.narooteo.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.annotation.security.AccountID;
import org.dongguk.vsa.mulmoori.core.dto.ResponseDto;
import org.dongguk.vsa.mulmoori.narooteo.usecase.ReadNarooteoOverviewListUsingUserUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/narooteos")
public class UserNarooteoQueryV1Controller {

    private final ReadNarooteoOverviewListUsingUserUseCase readNarooteoOverviewListUsingUserUseCase;

    @GetMapping("/overviews")
    public ResponseDto<?> readNarooteoOverviews(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readNarooteoOverviewListUsingUserUseCase.execute(accountId));
    }
}
