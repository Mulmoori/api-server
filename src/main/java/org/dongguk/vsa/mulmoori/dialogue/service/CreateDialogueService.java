package org.dongguk.vsa.mulmoori.dialogue.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.dialogue.domain.Dialogue;
import org.dongguk.vsa.mulmoori.dialogue.dto.request.CreateDialogueRequestDto;
import org.dongguk.vsa.mulmoori.dialogue.event.CreateDialogueEvent;
import org.dongguk.vsa.mulmoori.dialogue.repository.DialogueRepository;
import org.dongguk.vsa.mulmoori.dialogue.usecase.CreateDialogueUseCase;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.narooteo.repository.NarooteoRepository;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.dongguk.vsa.mulmoori.user.domain.mysql.UserNarooteo;
import org.dongguk.vsa.mulmoori.user.repository.UserNarooteoRepository;
import org.dongguk.vsa.mulmoori.user.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateDialogueService implements CreateDialogueUseCase {

    private final UserRepository userRepository;
    private final NarooteoRepository narooteoRepository;
    private final UserNarooteoRepository userNarooteoRepository;

    private final DialogueRepository dialogueRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void execute(UUID accountId, CreateDialogueRequestDto requestDto) {
        // 1. 사용자 및 나루터 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        Narooteo narooteo = narooteoRepository.findById(requestDto.narooteoId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 2. 권한 확인
        userNarooteoRepository.findByUserAndNarooteo(user, narooteo)
                .orElseThrow(() -> new CommonException(ErrorCode.ACCESS_DENIED));

        // 3. 대화 생성
        Dialogue dialogue = dialogueRepository.save(Dialogue.builder()
                .question(requestDto.question())
                .user(user)
                .narooteo(narooteo)
                .build()
        );

        // 4. Create Dialogue Event 발행
        applicationEventPublisher.publishEvent(CreateDialogueEvent.builder()
                .id(dialogue.getId())
                .question(dialogue.getQuestion())
                .build()
        );
    }
}
