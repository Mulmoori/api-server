package org.dongguk.vsa.mulmoori.user.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.annotation.security.AccountID;
import org.dongguk.vsa.mulmoori.core.dto.ResponseDto;
import org.dongguk.vsa.mulmoori.user.usecase.ReadUserUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserQueryV1Controller {

    private final ReadUserUseCase readUserUseCase;

    @GetMapping("")
    public ResponseDto<?> readUser(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserUseCase.execute(accountId));
    }
}
