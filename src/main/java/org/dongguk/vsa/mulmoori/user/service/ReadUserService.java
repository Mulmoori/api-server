package org.dongguk.vsa.mulmoori.user.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.dialogue.domain.Dialogue;
import org.dongguk.vsa.mulmoori.dialogue.repository.DialogueRepository;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.dongguk.vsa.mulmoori.user.domain.mysql.UserNarooteo;
import org.dongguk.vsa.mulmoori.user.dto.response.UserDetailDto;
import org.dongguk.vsa.mulmoori.user.repository.UserNarooteoRepository;
import org.dongguk.vsa.mulmoori.user.repository.UserRepository;
import org.dongguk.vsa.mulmoori.user.usecase.ReadUserUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserService implements ReadUserUseCase {

    private final UserRepository userRepository;
    private final UserNarooteoRepository userNarooteoRepository;

    private final DialogueRepository dialogueRepository;

    @Override
    public UserDetailDto execute(UUID accountId) {
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        List<UserNarooteo> narooteos = userNarooteoRepository.findByUser(user);

        List<Dialogue> dialogues = dialogueRepository.findByUser(user);

        return UserDetailDto.builder()
                .nickname(user.getNickname())
                .participantNarooteoCount(narooteos.size())
                .participantDialogueCount(dialogues.size())
                .build();
    }
}
