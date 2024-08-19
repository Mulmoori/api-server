package org.dongguk.vsa.mulmoori.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.annotation.security.AccountID;
import org.dongguk.vsa.mulmoori.core.dto.ResponseDto;
import org.dongguk.vsa.mulmoori.user.dto.request.CreateUserNarooteoRequestDto;
import org.dongguk.vsa.mulmoori.user.usecase.CreateUserNarooteoUseCase;
import org.dongguk.vsa.mulmoori.user.usecase.DeleteUserNarooteoUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/narooteos")
public class UserNarooteoCommandV1Controller {

    private final CreateUserNarooteoUseCase createUserNarooteoUseCase;
    private final DeleteUserNarooteoUseCase deleteUserNarooteoUseCase;

    @PostMapping("")
    public ResponseDto<?> createNarooteoUser(
            @AccountID UUID accountId,
            @RequestBody @Valid CreateUserNarooteoRequestDto requestDto
    ) {
        createUserNarooteoUseCase.execute(accountId, requestDto);

        return ResponseDto.created(null);
    }

    @DeleteMapping("/{narooteoId}")
    public ResponseDto<?> deleteNarooteoUser(
            @AccountID UUID accountId,
            @PathVariable Long narooteoId
    ) {
        deleteUserNarooteoUseCase.execute(accountId, narooteoId);

        return ResponseDto.noContent();
    }
}
