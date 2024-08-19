package org.dongguk.vsa.mulmoori.dialogue.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.dialogue.domain.Dialogue;
import org.dongguk.vsa.mulmoori.dialogue.dto.response.DialogueOverviewListDto;
import org.dongguk.vsa.mulmoori.dialogue.repository.DialogueRepository;
import org.dongguk.vsa.mulmoori.dialogue.usecase.ReadDialogueOverviewListUsingUserUseCase;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.narooteo.repository.NarooteoRepository;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.dongguk.vsa.mulmoori.user.repository.UserNarooteoRepository;
import org.dongguk.vsa.mulmoori.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadDialogueOverviewListUsingUserService implements ReadDialogueOverviewListUsingUserUseCase {

    private final UserRepository userRepository;
    private final NarooteoRepository narooteoRepository;
    private final UserNarooteoRepository userNarooteoRepository;

    private final DialogueRepository dialogueRepository;

    @Override
    public DialogueOverviewListDto execute(UUID accountId, Long narooteoId) {
        // 1. 사용자 및 나루터 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        Narooteo narooteo = narooteoRepository.findById(narooteoId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 2. 권한 확인
        userNarooteoRepository.findByUserAndNarooteo(user, narooteo)
                .orElseThrow(() -> new CommonException(ErrorCode.ACCESS_DENIED));

        // 3. 전체 대화 목록 조회
        List<Dialogue> dialogues = dialogueRepository.findAllByUserAndNarooteo(user, narooteo);

        // 반환
        return DialogueOverviewListDto.fromEntities(dialogues);
    }
}
