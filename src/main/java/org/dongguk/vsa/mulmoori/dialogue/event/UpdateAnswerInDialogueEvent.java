package org.dongguk.vsa.mulmoori.dialogue.event;

import lombok.Builder;

@Builder
public record UpdateAnswerInDialogueEvent(
        Long id,
        String question,
        String answer
) {
}
