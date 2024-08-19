package org.dongguk.vsa.mulmoori.dialogue.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.core.utility.AnalysisUtil;
import org.dongguk.vsa.mulmoori.dialogue.domain.Dialogue;
import org.dongguk.vsa.mulmoori.dialogue.domain.type.EDialogueStatus;
import org.dongguk.vsa.mulmoori.dialogue.dto.request.UpdateAnswerInDialogueRequestDto;
import org.dongguk.vsa.mulmoori.dialogue.event.UpdateAnswerInDialogueEvent;
import org.dongguk.vsa.mulmoori.dialogue.repository.DialogueRepository;
import org.dongguk.vsa.mulmoori.dialogue.usecase.UpdateAnswerInDialogueUseCase;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.narooteo.repository.NarooteoRepository;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.dongguk.vsa.mulmoori.user.domain.mysql.UserNarooteo;
import org.dongguk.vsa.mulmoori.user.domain.type.ENarooteoRole;
import org.dongguk.vsa.mulmoori.user.repository.UserNarooteoRepository;
import org.dongguk.vsa.mulmoori.user.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateAnswerInDialogueService implements UpdateAnswerInDialogueUseCase {

    private final UserRepository userRepository;
    private final NarooteoRepository narooteoRepository;
    private final UserNarooteoRepository userNarooteoRepository;

    private final DialogueRepository dialogueRepository;

    private final AnalysisUtil analysisUtil;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(UUID accountId, Long dialogueId, UpdateAnswerInDialogueRequestDto requestDto) {
        // 1. 정보 조회
        Dialogue dialogue = dialogueRepository.findWithUserAndNarooteoById(dialogueId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        User requestUser = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Narooteo dialogueNarooteo = dialogue.getNarooteo();

        // 2. 권한 확인
        UserNarooteo userNarooteo = userNarooteoRepository.findByUserAndNarooteo(requestUser, dialogueNarooteo)
                .orElseThrow(() -> new CommonException(ErrorCode.ACCESS_DENIED));

        if (userNarooteo.getRole() != ENarooteoRole.HOST) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        // 3. 답변 업데이트
        String finalAnswer = requestDto.answer();

        if (requestDto.isUsingStt()) {
            try {
                finalAnswer = analysisUtil.preProcessSttText(
                        dialogue.getQuestion(),
                        requestDto.answer()
                );
            } catch (Exception e) {
                throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }

        dialogue.updateAnswer(
                finalAnswer,
                false,
                EDialogueStatus.COMPLETED
        );

        // 4. Update Answer In Dialogue Event 발행
        applicationEventPublisher.publishEvent(UpdateAnswerInDialogueEvent.builder()
                .id(dialogue.getId())
                .question(dialogue.getQuestion())
                .answer(dialogue.getAnswer())
                .build()
        );
    }
}
