package org.dongguk.vsa.mulmoori.core.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.vsa.mulmoori.dialogue.event.CreateDialogueEvent;
import org.dongguk.vsa.mulmoori.dialogue.event.UpdateAnswerInDialogueEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AIListener {

    @Async
    @EventListener(classes = {CreateDialogueEvent.class})
    public void handleCreateDialogueEvent(CreateDialogueEvent event) {
        // TODO: AI Server 와 통신이 필요함
    }

    @Async
    @EventListener(classes = {UpdateAnswerInDialogueEvent.class})
    public void handleUpdateAnswerInDialogueEvent(UpdateAnswerInDialogueEvent event) {
        // TODO: AI Server 와 통신이 필요함
    }
}
