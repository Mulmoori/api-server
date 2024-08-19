package org.dongguk.vsa.mulmoori.dialogue.event;

import lombok.Builder;

@Builder
public record UpdateAnswerInDialogueEvent(
        Long dialogueId,
        String question,
        String answer
) {
}
