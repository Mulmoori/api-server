package org.dongguk.vsa.mulmoori.core.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.vsa.mulmoori.core.utility.AnalysisUtil;
import org.dongguk.vsa.mulmoori.dialogue.domain.Dialogue;
import org.dongguk.vsa.mulmoori.dialogue.domain.type.EDialogueStatus;
import org.dongguk.vsa.mulmoori.dialogue.event.CreateDialogueEvent;
import org.dongguk.vsa.mulmoori.dialogue.event.UpdateAnswerInDialogueEvent;
import org.dongguk.vsa.mulmoori.dialogue.repository.DialogueRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AIListener {

    private final AnalysisUtil analysisUtil;

    private final DialogueRepository dialogueRepository;

    @Async
    @EventListener(classes = {CreateDialogueEvent.class})
    public void handleCreateDialogueEvent(CreateDialogueEvent event) {
        String answer = null;

        try {
            answer = analysisUtil.createAnswer(
                    event.id(),
                    event.question()
            );
        } catch (Exception ignored) {
        }

        Optional<Dialogue> dialogue = dialogueRepository.findById(event.id());

        if (dialogue.isEmpty()) {
            return;
        }

        if (answer == null) {
            dialogue.get().updateStatus(EDialogueStatus.PROGRESS);
        } else {
            dialogue.get().updateAnswer(
                    answer,
                    true,
                    EDialogueStatus.COMPLETED
            );
        }
    }

    @Async
    @EventListener(classes = {UpdateAnswerInDialogueEvent.class})
    public void handleUpdateAnswerInDialogueEvent(UpdateAnswerInDialogueEvent event) {
        try {
            analysisUtil.updateVectorStore(
                    event.id(),
                    event.question(),
                    event.answer()
            );
        }catch (Exception ignored){
        }
    }
}
